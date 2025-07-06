package com.litwnb.reelindex.service;

import com.litwnb.reelindex.model.Genre;
import com.litwnb.reelindex.model.MovieDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface MovieService {
    Page<MovieDTO> listMovies(String movieTitle, String director, Genre genre, Integer pageNumber, Integer moviesPerPage);

    MovieDTO getMovieById(UUID id);
}
