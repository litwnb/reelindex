package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.model.UserPrincipal;
import com.litwnb.reelindex.service.UserService;
import com.litwnb.reelindex.util.MovieErrorResponse;
import com.litwnb.reelindex.util.UsernameOccupiedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private static final String REGISTER_PATH = "/register";
    private static final String USER_PATH = "/user";

    private final UserService userService;

    @PostMapping(REGISTER_PATH)
    public UserPrincipal register(@RequestBody User user) {
        return new UserPrincipal(userService.register(user));
    }

    @GetMapping(USER_PATH)
    public UserPrincipal getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userPrincipal;
    }

    @ExceptionHandler(UsernameOccupiedException.class)
    private ResponseEntity<MovieErrorResponse> handleException() {
        return new ResponseEntity<>(new MovieErrorResponse(
                "This user already exists",
                System.currentTimeMillis()
        ), HttpStatus.BAD_REQUEST);
    }
}
