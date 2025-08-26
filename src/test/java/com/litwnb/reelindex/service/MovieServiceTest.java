package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.Movie;
import com.litwnb.reelindex.mapper.MovieMapper;
import com.litwnb.reelindex.model.Genre;
import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.repository.MovieRepository;
import com.litwnb.reelindex.util.MovieNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieServiceJPA movieService;

    @Test
    void listMovies_whenTitleProvided_callsFindAllByTitle() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by("title"));
        Page<Movie> moviePage = Page.empty();
        when(movieRepository.findAllByTitleIsLikeIgnoreCase("%test%", pageable)).thenReturn(moviePage);

        movieService.listMovies("test", null, null, null, null);

        verify(movieRepository).findAllByTitleIsLikeIgnoreCase("%test%", pageable);
        verify(movieMapper, never()).movieToMovieDto(any());
    }

    @Test
    void listMovies_whenDirectorProvided_callsFindAllByDirector() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by("title"));
        Page<Movie> moviePage = Page.empty();
        when(movieRepository.findAllByDirectorIsLikeIgnoreCase("%nolan%", pageable)).thenReturn(moviePage);

        movieService.listMovies(null, "nolan", null, null, null);

        verify(movieRepository).findAllByDirectorIsLikeIgnoreCase("%nolan%", pageable);
    }

    @Test
    void listMovies_whenGenreProvided_callsFindAllByGenre() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by("title"));
        Page<Movie> moviePage = Page.empty();
        when(movieRepository.findAllByGenre(Genre.ACTION, pageable)).thenReturn(moviePage);

        movieService.listMovies(null, null, Genre.ACTION, null, null);

        verify(movieRepository).findAllByGenre(Genre.ACTION, pageable);
    }

    @Test
    void getMovieById_whenMovieExists_returnsDto() {
        UUID id = UUID.randomUUID();
        Movie movie = new Movie();
        MovieDTO movieDTO = MovieDTO.builder().title("Test").build();

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieMapper.movieToMovieDto(movie)).thenReturn(movieDTO);

        MovieDTO result = movieService.getMovieById(id);

        assertEquals(movieDTO, result);
    }

    @Test
    void getMovieById_whenMovieNotExists_throwsException() {
        UUID id = UUID.randomUUID();
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getMovieById(id));
    }
}
