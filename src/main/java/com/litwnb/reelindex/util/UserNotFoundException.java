package com.litwnb.reelindex.util;

public class UserNotFoundException extends AppException {
    public UserNotFoundException() {
        super("User not found");
    }
}
