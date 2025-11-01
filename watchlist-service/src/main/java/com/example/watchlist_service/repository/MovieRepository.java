package com.example.watchlist_service.repository;

import com.example.watchlist_service.dao.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {    

    // Fetch all movies ordered by ID in descending order (newest first)
    List<Movie> findAllByOrderByIdDesc();
}
