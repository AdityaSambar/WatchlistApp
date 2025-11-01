package com.example.watchlist_service.controller;

import com.example.watchlist_service.dao.Movie;
import com.example.watchlist_service.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller that exposes REST endpoints for managing the Watchlist movies.
 */
@RestController
@RequestMapping("/watchlist")
@CrossOrigin(origins = "*") // allow cross-origin requests (for frontend)
public class MovieWatchlistDBController {

    @Autowired
    private DatabaseService databaseService;

    /**
     * Add a new movie to the watchlist.
     */
    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie) {
        return databaseService.addMovie(movie);
    }

    /**
     * Fetch all movies from the watchlist (most recent first).
     */
    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return databaseService.getAllMovies();
    }

    /**
     * Fetch a movie by its TMDB ID.
     */
    @GetMapping("/{tmdbId}")
    public Optional<Movie> getMovieById(@PathVariable Long tmdbId) {
        return databaseService.getMovieById(tmdbId);
    }

    /**
     * Delete a movie by its TMDB ID.
     */
    @DeleteMapping("/delete/{tmdbId}")
    public boolean deleteMovie(@PathVariable Long tmdbId) {
        return databaseService.deleteMovie(tmdbId);
    }
}
