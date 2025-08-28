package com.litwnb.reelindex.util;

import java.util.NoSuchElementException;

public class RatingNotFoundException extends NoSuchElementException {
    public RatingNotFoundException() {
        super("User has not rated this movie yet");
    }
}
