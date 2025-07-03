package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.Genre;
import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return movieService.getMovieById(movieId).orElseThrow();
    }
}
