package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.Genre;
import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.service.MovieService;
import com.litwnb.reelindex.util.BookNotFoundException;
import com.litwnb.reelindex.util.MovieErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MovieController {
    public static final String MOVIE_ID = "/{movieId}";

    private final MovieService movieService;

    @GetMapping
    public PagedModel<MovieDTO> listMovies(@RequestParam(required = false) String title,
                                           @RequestParam(required = false) String director,
                                           @RequestParam(required = false) Genre genre,
                                           @RequestParam(required = false) Integer pageNumber,
                                           @RequestParam(required = false) Integer moviesPerPage) {
        return new PagedModel<>(movieService.listMovies(title, director, genre, pageNumber, moviesPerPage));
    }

    @GetMapping(value = MOVIE_ID)
    public MovieDTO getMovieById(@PathVariable("movieId") UUID movieId) {
        return movieService.getMovieById(movieId);
    }

    @ExceptionHandler(BookNotFoundException.class)
    private ResponseEntity<MovieErrorResponse> handleException() {
        MovieErrorResponse response = new MovieErrorResponse(
                "Movie with this id was not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
