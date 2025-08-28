package com.litwnb.reelindex.util;

public class InvalidRatingException extends AppException {
    public InvalidRatingException() {
        super("Rating must be between 1 and 10");
    }
}
