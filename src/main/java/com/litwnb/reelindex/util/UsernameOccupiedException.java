package com.litwnb.reelindex.util;

public class UsernameOccupiedException extends AppException {
    public UsernameOccupiedException() {
        super("This user already exists");
    }
}
