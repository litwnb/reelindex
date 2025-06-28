package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieDTO> listMovies() {
        return movieService.listMovies();
    }
}
