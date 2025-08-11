package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.Genre;
import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.service.MovieService;
import com.litwnb.reelindex.util.MovieErrorResponse;
import com.litwnb.reelindex.util.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public Page<MovieDTO> listMovies(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String director,
                                     @RequestParam(required = false) Genre genre,
                                     @RequestParam(required = false) Integer pageNumber,
                                     @RequestParam(required = false) Integer moviesPerPage) {
        return movieService.listMovies(title, director, genre, pageNumber, moviesPerPage);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable("movieId") UUID movieId) {
        return ResponseEntity.ok(movieService.getMovieById(movieId));
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<MovieErrorResponse> handleMovieNotFoundException() {
        MovieErrorResponse response = new MovieErrorResponse(
                "Movie with this id was not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
