package com.example.watchlist_service.controller;

import com.example.watchlist_service.dao.Movie;
import com.example.watchlist_service.service.TMDBService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to expose TMDB search functionality to frontend.
 */
@RestController
@RequestMapping("/tmdb")
@CrossOrigin(origins = "*")
public class TMDBSearchController {

    private final TMDBService tmdbService;

    public TMDBSearchController(TMDBService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @GetMapping("/search/movie/{query}")
    public List<Movie> searchMovies(@PathVariable String query) {
        return tmdbService.searchMovies(query);
    }

    @GetMapping("search/tv/{query}")
    public List<Movie> searchTVShows(@PathVariable String query) {
        return tmdbService.searchTVShows(query);
    }
}
