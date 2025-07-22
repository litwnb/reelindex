package com.litwnb.reelindex.repository;

import com.litwnb.reelindex.entity.MovieRating;
import com.litwnb.reelindex.entity.MovieRatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MovieRatingRepository extends JpaRepository<MovieRating, MovieRatingKey> {
    List<MovieRating> findAllByMovieId(UUID movieId);

    List<MovieRating> findAllByUserId(UUID userId);

    @Query("SELECT AVG(r.rating) FROM MovieRating r WHERE r.movie.id = :movieId")
    Double averageForMovie(@Param("movieId") UUID movieId);
}
