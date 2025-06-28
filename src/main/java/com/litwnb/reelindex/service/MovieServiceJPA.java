package com.litwnb.reelindex.service;

import com.litwnb.reelindex.mapper.MovieMapper;
import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceJPA implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDTO> listMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }
}
