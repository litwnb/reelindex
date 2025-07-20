package com.litwnb.reelindex.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovieRatingDTO {
    private UUID userId;
    private int rating;
    private LocalDateTime ratedAt;
}
