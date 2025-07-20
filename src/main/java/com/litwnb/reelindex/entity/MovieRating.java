package com.litwnb.reelindex.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MovieRating {
    @EmbeddedId
    private MovieRatingKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    private Movie movie;

    @Min(value = 1)
    @Max(value = 10)
    private int rating;

    private LocalDateTime ratedAt = LocalDateTime.now();
}
