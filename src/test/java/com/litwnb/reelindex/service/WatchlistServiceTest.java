package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.Movie;
import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.mapper.MovieMapper;
import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.repository.MovieRepository;
import com.litwnb.reelindex.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WatchlistServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;
    @InjectMocks
    private WatchlistServiceJPA watchlistService;
    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void getWatchlist_returnsDtos() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Movie movie = new Movie();
        user.setWatchlist(Set.of(movie));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieMapper.movieToMovieDto(movie)).thenReturn(MovieDTO.builder().build());

        Set<MovieDTO> result = watchlistService.getWatchlist(userId);

        assertEquals(1, result.size());
        verify(userRepository).findById(userId);
    }

    @Test
    void addMovieToWatchlist_addsMovieAndSavesUser() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();

        User user = User.builder().watchlist(new HashSet<>()).build();
        Movie movie = Movie.builder().id(movieId).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        watchlistService.addMovieToWatchlist(userId, movieId);

        verify(userRepository).save(userCaptor.capture());
        User actualUser = userCaptor.getValue();

        assertTrue(actualUser.getWatchlist().contains(movie));
    }

    @Test
    void removeMovieFromWatchlist_removesMovieAndSavesUser() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();

        Movie movie = Movie.builder().id(movieId).build();
        User user = User.builder().watchlist(new HashSet<>(Set.of(movie))).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        watchlistService.removeMovieFromWatchlist(userId, movieId);

        verify(userRepository).save(userCaptor.capture());
        User actualUser = userCaptor.getValue();

        assertFalse(actualUser.getWatchlist().contains(movie));
    }
}
