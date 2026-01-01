package com.example.watchlist_service.controller;

import com.example.watchlist_service.dao.User;
import com.example.watchlist_service.dto.auth.AuthResponse;
import com.example.watchlist_service.dto.auth.LoginRequest;
import com.example.watchlist_service.dto.auth.RegisterRequest;
import com.example.watchlist_service.security.JwtUtil;
import com.example.watchlist_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {

        User user = authService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse(token, user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        User user = authService.authenticate(
                request.getUsernameOrEmail(),
                request.getPassword()
        );

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(
                new AuthResponse(token, user.getUsername())
        );
    }
}
