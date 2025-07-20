package com.litwnb.reelindex.model;

import lombok.Data;

import java.util.UUID;

@Data
public class RatingRequest {
    private UUID movieId;
    private int rating;
}
