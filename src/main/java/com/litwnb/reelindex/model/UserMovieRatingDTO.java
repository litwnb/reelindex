package com.litwnb.reelindex.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserMovieRatingDTO {
    private UUID movieId;
    private String movieTitle;
    private int rating;
    private LocalDateTime ratedAt;
}
