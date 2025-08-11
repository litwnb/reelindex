package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.model.UserPrincipal;
import com.litwnb.reelindex.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/watchlist")
@RequiredArgsConstructor
public class WatchlistController {
    private final WatchlistService watchlistService;

    @GetMapping()
    public ResponseEntity<Set<MovieDTO>> getWatchlist(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(watchlistService.getWatchlist(userPrincipal.getUser().getId()));
    }

    @PostMapping()
    public ResponseEntity<String> addMovie(@RequestParam String movieId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        watchlistService.addMovieToWatchlist(userPrincipal.getUser().getId(), UUID.fromString(movieId));
        return ResponseEntity.ok("Movie added");
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable String movieId,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        watchlistService.removeMovieFromWatchlist(userPrincipal.getUser().getId(), UUID.fromString(movieId));
        return ResponseEntity.ok("Movie removed");
    }
}
