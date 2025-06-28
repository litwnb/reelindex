package com.litwnb.reelindex.mapper;

import com.litwnb.reelindex.entity.Movie;
import com.litwnb.reelindex.model.MovieDTO;
import org.mapstruct.Mapper;

@Mapper
public interface MovieMapper {
    Movie movieDtoToMovie(MovieDTO dto);

    MovieDTO movieToMovieDto(Movie movie);
}
