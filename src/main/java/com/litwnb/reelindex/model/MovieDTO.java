package com.litwnb.reelindex.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MovieDTO {
    private UUID id;
    @NotBlank
    private String title;
    @NotNull
    private Integer releaseYear;
    @NotBlank
    private String runtime;
    @NotNull
    private Genre genre;
    private String overview;
    private Float imdbScore;
    private Integer metaScore;
    private double averageRating;
    @NotBlank
    private String director;
    private String posterLink;
}
