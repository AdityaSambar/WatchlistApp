package com.example.watchlist_service.service;

import com.example.watchlist_service.dao.Movie;
import com.example.watchlist_service.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Handles all CRUD operations for Movie entity
@Service
public class DatabaseService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        // Return watchlisted movies. Most recent first
        return movieRepository.findAllByOrderByIdDesc();
    }

    // Get movie by TMDB ID
    public Optional<Movie> getMovieById(Long tmdbId) {
        return movieRepository.findAll().stream().filter(m -> m.getTmdbId().equals(tmdbId)).findFirst();
    }

    // Delete movie by TMDB ID
    public boolean deleteMovie(Long tmdbId) {
        Optional<Movie> movie = getMovieById(tmdbId);
        if(movie.isPresent()) {
            movieRepository.delete(movie.get());
            return true;
        }
        return false;
    }
}  
