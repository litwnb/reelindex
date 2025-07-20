package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.MovieRating;
import com.litwnb.reelindex.entity.MovieRatingKey;
import com.litwnb.reelindex.model.MovieRatingDTO;
import com.litwnb.reelindex.model.UserMovieRatingDTO;
import com.litwnb.reelindex.repository.MovieRatingRepository;
import com.litwnb.reelindex.repository.MovieRepository;
import com.litwnb.reelindex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieRatingServiceJPA implements MovieRatingService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final MovieRatingRepository ratingRepository;

    @Override
    @Transactional
    public void rateMovie(UUID userId, UUID movieId, int rating) {
        if (rating < 1 || rating > 10)
            throw new IllegalArgumentException("Rating must be between 1 and 10.");

        MovieRatingKey key = new MovieRatingKey(userId, movieId);
        MovieRating movieRating = ratingRepository.findById(key)
                .orElseGet(() -> {
                    MovieRating newRating = new MovieRating();
                    newRating.setId(key);
                    newRating.setUser(userRepository.getReferenceById(userId));
                    newRating.setMovie(movieRepository.getReferenceById(movieId));
                    return newRating;
                });

        movieRating.setRating(rating);
        movieRating.setRatedAt(LocalDateTime.now());
        ratingRepository.save(movieRating);
    }

    @Override
    public Integer getUserRating(UUID userId, UUID movieId) {
        return ratingRepository.findById(new MovieRatingKey(userId, movieId))
                .map(MovieRating::getRating)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserMovieRatingDTO> getAllRatingsByUser(UUID userId) {
        return ratingRepository.findAllByUserId(userId)
                .stream()
                .map(rating -> new UserMovieRatingDTO(
                        rating.getMovie().getId(),
                        rating.getMovie().getTitle(),
                        rating.getRating(),
                        rating.getRatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieRatingDTO> getRatingsForMovie(UUID movieId) {
        return ratingRepository.findAllByMovieId(movieId)
                .stream()
                .map(rating -> new MovieRatingDTO(
                        rating.getUser().getId(),
                        rating.getRating(),
                        rating.getRatedAt()))
                .collect(Collectors.toList());
    }
}
