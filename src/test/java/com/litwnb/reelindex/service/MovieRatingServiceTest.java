package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.Movie;
import com.litwnb.reelindex.entity.MovieRating;
import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.repository.MovieRatingRepository;
import com.litwnb.reelindex.repository.MovieRepository;
import com.litwnb.reelindex.repository.UserRepository;
import com.litwnb.reelindex.util.InvalidRatingException;
import com.litwnb.reelindex.util.RatingNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieRatingServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MovieRatingRepository ratingRepository;
    @InjectMocks
    private MovieRatingServiceJPA ratingService;
    @Captor
    private ArgumentCaptor<MovieRating> ratingCaptor;

    @Test
    void rateMovie_whenInvalidRating_throwsException() {
        assertThrows(InvalidRatingException.class,
                () -> ratingService.rateMovie(UUID.randomUUID(), UUID.randomUUID(), 15));
    }

    @Test
    void rateMovie_savesRatingAndUpdatesAverage() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        User user = User.builder().id(userId).username("username").build();
        Movie movie = Movie.builder().id(movieId).build();

        when(ratingRepository.findById(any())).thenReturn(Optional.empty());
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(movieRepository.getReferenceById(movieId)).thenReturn(movie);

        when(ratingRepository.averageForMovie(movieId)).thenReturn(7.5);
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        ratingService.rateMovie(userId, movieId, 8);

        verify(ratingRepository).save(ratingCaptor.capture());
        MovieRating savedRating = ratingCaptor.getValue();

        assertEquals(8, savedRating.getRating());
        assertEquals(userId, savedRating.getUser().getId());
        assertEquals(movieId, savedRating.getMovie().getId());
    }

    @Test
    void getUserRating_returnsValue() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        MovieRating rating = new MovieRating();
        rating.setRating(7);

        when(ratingRepository.findById(any())).thenReturn(Optional.of(rating));

        Integer result = ratingService.getUserRating(userId, movieId);

        assertEquals(7, result);
    }

    @Test
    void getUserRating_whenNoRating_throwsRatingNotFoundException() {
        when(ratingRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RatingNotFoundException.class,
                () -> ratingService.getUserRating(UUID.randomUUID(), UUID.randomUUID()));
    }
}
