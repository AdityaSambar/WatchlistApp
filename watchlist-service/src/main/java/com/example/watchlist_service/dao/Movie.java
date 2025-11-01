package com.example.watchlist_service.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "watchlist_movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key for database. Fetch data such that the highest ID is shown first(so that the most recently added movies show up on top of the watchlist)

    private Long tmdbId; // TMDb movie ID
    private String title; 
    private String posterPath;
    private String releaseDate;
    private String overview;
    private Double voteAverage;
    private Integer voteCount; //Use this to rank the movies when using the search function, most vote counted movie gets put on top of search results

    // Constructors
    public Movie() {}

    public Movie(Long tmdbId, String title, String posterPath, String releaseDate, String overview, Double voteAverage, Integer voteCount) {
        this.tmdbId = tmdbId;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    // Getters and setters

    public Integer getVoteCount() { return voteCount; }
    public void setVoteCount(Integer voteCount) { this.voteCount = voteCount; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTmdbId() { return tmdbId; }
    public void setTmdbId(Long tmdbId) { this.tmdbId = tmdbId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public Double getVoteAverage() { return voteAverage; }
    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }
}
