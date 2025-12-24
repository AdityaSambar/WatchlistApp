package com.example.watchlist_service.service;

import com.example.watchlist_service.dao.User;
import com.example.watchlist_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String email, String rawPassword) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(username, email, hashedPassword);

        return userRepository.save(user);
    }

    public User authenticate(String usernameOrEmail, String rawPassword) {

        Optional<User> userOpt = userRepository.findByUsername(usernameOrEmail);

        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(usernameOrEmail);
        }

        User user = userOpt.orElseThrow(() ->
                new IllegalArgumentException("Invalid credentials")
        );

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return user;
    }
}
