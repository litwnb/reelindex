package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.Movie;
import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.mapper.MovieMapper;
import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.repository.MovieRepository;
import com.litwnb.reelindex.repository.UserRepository;
import com.litwnb.reelindex.util.MovieNotFoundException;
import com.litwnb.reelindex.util.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistServiceJPA implements WatchlistService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    @Transactional(readOnly = true)
    public Set<MovieDTO> getWatchlist(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new)
                .getWatchlist()
                .stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void addMovieToWatchlist(UUID userId, UUID movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(MovieNotFoundException::new);

        user.getWatchlist().add(movie);
        userRepository.save(user);
    }

    @Override
    public void removeMovieFromWatchlist(UUID userId, UUID movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(MovieNotFoundException::new);

        user.getWatchlist().remove(movie);
        userRepository.save(user);
    }
}
