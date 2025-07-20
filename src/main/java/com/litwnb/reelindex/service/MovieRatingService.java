package com.litwnb.reelindex.service;

import com.litwnb.reelindex.model.MovieRatingDTO;
import com.litwnb.reelindex.model.UserMovieRatingDTO;

import java.util.List;
import java.util.UUID;

public interface MovieRatingService {
    void rateMovie(UUID userId, UUID movieId, int rating);

    Integer getUserRating(UUID userId, UUID movieId);

    List<UserMovieRatingDTO> getAllRatingsByUser(UUID userId);

    List<MovieRatingDTO> getRatingsForMovie(UUID movieId);
}
