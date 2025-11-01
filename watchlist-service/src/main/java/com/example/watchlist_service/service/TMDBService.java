package com.example.watchlist_service.service;

import com.example.watchlist_service.dao.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Service class responsible for fetching movie data from TMDB API.
 * Uses the Read Access Token for authorization.
 */
@Service
public class TMDBService {

    private final RestTemplate restTemplate;

    @Value("${tmdb.read.token}")
    private String readAccessToken;

    public TMDBService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Searches for movies on TMDB using a text query.
     * Returns a list of Movie objects mapped from the TMDB JSON response.
     *
     * @param query Movie title or keyword to search for.
     * @return List of Movie objects.
     */
    public List<Movie> searchMovies(String query) {
        List<Movie> movies = new ArrayList<>();

        try {
            String url = "https://api.themoviedb.org/3/search/movie?query=" + query;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + readAccessToken);
            headers.set("accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();

            if (response.getStatusCode() == HttpStatus.OK && body != null && body.containsKey("results")) {
                Object resultsObj = body.get("results");

                if (resultsObj instanceof List<?>) {
                    List<?> results = (List<?>) resultsObj;

                    for (Object obj : results) {
                        if (obj instanceof Map<?, ?> movieMap) {
                            Movie movie = new Movie();

                            Object idObj = movieMap.get("id");
                            if (idObj instanceof Number)
                                movie.setTmdbId(((Number) idObj).longValue());

                            movie.setTitle((String) movieMap.get("title"));
                            movie.setOverview((String) movieMap.get("overview"));
                            movie.setPosterPath((String) movieMap.get("poster_path"));
                            movie.setReleaseDate((String) movieMap.get("release_date"));

                            Object voteAvg = movieMap.get("vote_average");
                            if (voteAvg instanceof Number)
                                movie.setVoteAverage(((Number) voteAvg).doubleValue());

                            Object voteCnt = movieMap.get("vote_count");
                            if (voteCnt instanceof Number)
                                movie.setVoteCount(((Number) voteCnt).intValue());

                            movies.add(movie);
                        }
                    }

                    // Sort by voteCount descending (most popular first)
                    movies.sort(Comparator.comparing(Movie::getVoteCount, Comparator.nullsLast(Comparator.reverseOrder())));
                }
            } else {
                System.err.println("TMDB API returned status: " + response.getStatusCode());
            }

        } catch (RestClientException e) {
            System.err.println("Error calling TMDB API: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error parsing TMDB data: " + e.getMessage());
        }

        return movies;
    }


    /**
     * Searches for TV shows on TMDB using a text query.
     * Returns a list of Movie objects mapped from the TMDB JSON response.
     *
     * @param query TV show title or keyword to search for.
     * @return List of Movie objects.
     */
    public List<Movie> searchTVShows(String query) {
        List<Movie> movies = new ArrayList<>();

        try {
            String url = "https://api.themoviedb.org/3/search/tv?query=" + query;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + readAccessToken);
            headers.set("accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();

            if (response.getStatusCode() == HttpStatus.OK && body != null && body.containsKey("results")) {
                Object resultsObj = body.get("results");

                if (resultsObj instanceof List<?>) {
                    List<?> results = (List<?>) resultsObj;

                    for (Object obj : results) {
                        if (obj instanceof Map<?, ?> movieMap) {
                            Movie movie = new Movie();

                            Object idObj = movieMap.get("id");
                            if (idObj instanceof Number)
                                movie.setTmdbId(((Number) idObj).longValue());

                            movie.setTitle((String) movieMap.get("original_name"));
                            movie.setOverview((String) movieMap.get("overview"));
                            movie.setPosterPath((String) movieMap.get("poster_path"));
                            movie.setReleaseDate((String) movieMap.get("release_date"));

                            Object voteAvg = movieMap.get("vote_average");
                            if (voteAvg instanceof Number)
                                movie.setVoteAverage(((Number) voteAvg).doubleValue());

                            Object voteCnt = movieMap.get("vote_count");
                            if (voteCnt instanceof Number)
                                movie.setVoteCount(((Number) voteCnt).intValue());

                            movies.add(movie);
                        }
                    }

                    // Sort by voteCount descending (most popular first)
                    movies.sort(Comparator.comparing(Movie::getVoteCount, Comparator.nullsLast(Comparator.reverseOrder())));
                }
            } else {
                System.err.println("TMDB API returned status: " + response.getStatusCode());
            }

        } catch (RestClientException e) {
            System.err.println("Error calling TMDB API: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error parsing TMDB data: " + e.getMessage());
        }

        return movies;
    }
}
