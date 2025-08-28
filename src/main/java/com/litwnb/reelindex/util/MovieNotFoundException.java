package com.litwnb.reelindex.util;

import java.util.NoSuchElementException;

public class MovieNotFoundException extends NoSuchElementException {
    public MovieNotFoundException() {
        super("Movie with the specified ID not found");
    }
}
