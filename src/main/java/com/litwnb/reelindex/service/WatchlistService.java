package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.Movie;

import java.util.Set;
import java.util.UUID;

public interface WatchlistService {
    Set<Movie> getWatchlist(UUID userId);
    void addMovieToWatchlist(UUID userId, UUID movieId);
    void removeMovieFromWatchlist(UUID userId, UUID movieId);
}
