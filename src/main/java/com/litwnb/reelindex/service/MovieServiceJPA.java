package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.Movie;
import com.litwnb.reelindex.mapper.MovieMapper;
import com.litwnb.reelindex.model.Genre;
import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieServiceJPA implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_MOVIES_PER_PAGE = 50;

    @Override
    public Page<MovieDTO> listMovies(String movieTitle, String director, Genre genre, Integer pageNumber, Integer moviesPerPage) {
        int queryPageNumber = (pageNumber != null && pageNumber > 0) ? (pageNumber - 1) : DEFAULT_PAGE;
        int queryMoviesPerPage = Objects.requireNonNullElse(moviesPerPage, DEFAULT_MOVIES_PER_PAGE);

        PageRequest pageRequest = PageRequest.of(queryPageNumber, queryMoviesPerPage, Sort.by("title"));
        Page<Movie> moviePage;

        if (movieTitle != null && !movieTitle.isEmpty()) {
            moviePage = listMoviesByTitle(movieTitle, pageRequest);
        } else if (director != null && !director.isEmpty()) {
            moviePage = listMoviesByDirector(director, pageRequest);
        } else if (genre != null) {
            moviePage = listMoviesByGenre(genre, pageRequest);
        } else {
            moviePage = movieRepository.findAll(pageRequest);
        }

        return moviePage.map(movieMapper::movieToMovieDto);
    }

    public Page<Movie> listMoviesByTitle(String title, Pageable pageable) {
        return movieRepository.findAllByTitleIsLikeIgnoreCase("%" + title + "%", pageable);
    }

    public Page<Movie> listMoviesByDirector(String director, Pageable pageable) {
        return movieRepository.findAllByDirectorIsLikeIgnoreCase("%" + director + "%", pageable);
    }

    public Page<Movie> listMoviesByGenre(Genre genre, Pageable pageable) {
        return movieRepository.findAllByGenre(genre, pageable);
    }

    @Override
    public Optional<MovieDTO> getMovieById(UUID id) {
        return Optional.ofNullable(movieMapper.movieToMovieDto(movieRepository.findById(id)
                .orElse(null)));
    }
}
