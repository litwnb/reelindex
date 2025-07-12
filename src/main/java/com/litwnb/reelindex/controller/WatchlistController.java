package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.UserPrincipal;
import com.litwnb.reelindex.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WatchlistController {
    private static final String WATCHLIST_PATH = "/user/watchlist";

    private final WatchlistService watchlistService;


    @GetMapping(WATCHLIST_PATH)
    public ResponseEntity<?> getWatchlist(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(watchlistService.getWatchlist(userPrincipal.getUser().getId()));
    }

    @PostMapping(WATCHLIST_PATH)
    public ResponseEntity<?> addMovie(@RequestParam Map<String, String> requestBody,
                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UUID movieId = UUID.fromString(requestBody.get("movieId"));
        UUID userId = userPrincipal.getUser().getId();

        watchlistService.addMovieToWatchlist(userId, movieId);
        return ResponseEntity.ok("Movie added");
    }

    @DeleteMapping(WATCHLIST_PATH)
    public ResponseEntity<?> deleteMovie(@RequestParam String movieId,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UUID userId = userPrincipal.getUser().getId();

        watchlistService.removeMovieFromWatchlist(userId, UUID.fromString(movieId));
        return ResponseEntity.ok("Movie removed");
    }
}
