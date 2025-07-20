package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.RatingRequest;
import com.litwnb.reelindex.model.UserMovieRatingDTO;
import com.litwnb.reelindex.model.UserPrincipal;
import com.litwnb.reelindex.service.MovieRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MovieRatingController {
    private static final String USER_RATINGS_PATH = "/user/ratings";
    private static final String USER_MOVIE_RATING_PATH = "/user/ratings/{movieId}";

    private final MovieRatingService movieRatingService;

    @PostMapping(USER_RATINGS_PATH)
    public ResponseEntity<?> rateMovie(@RequestBody RatingRequest request,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        movieRatingService.rateMovie(userPrincipal.getUser().getId(),
                                    request.getMovieId(),
                                    request.getRating());
        return ResponseEntity.ok("Rating submitted");
    }

    @GetMapping(USER_MOVIE_RATING_PATH)
    public ResponseEntity<Integer> getMovieRating(@PathVariable("movieId") String movieId,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UUID userId = userPrincipal.getUser().getId();
        UUID movieIdParsed = UUID.fromString(movieId);

        return ResponseEntity.ok(movieRatingService.getUserRating(userId, movieIdParsed));
    }

    @GetMapping(USER_RATINGS_PATH)
    public ResponseEntity<List<UserMovieRatingDTO>> getUserRatings(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(movieRatingService.getAllRatingsByUser(userPrincipal.getUser().getId()));
    }
}
