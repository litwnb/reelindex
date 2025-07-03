package com.litwnb.reelindex.repository;

import com.litwnb.reelindex.entity.Movie;
import com.litwnb.reelindex.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Page<Movie> findAllByTitleIsLikeIgnoreCase(String title, Pageable pageable);

    Page<Movie> findAllByDirectorIsLikeIgnoreCase(String director, Pageable pageable);

    Page<Movie> findAllByGenre(Genre genre, Pageable pageable);
}
