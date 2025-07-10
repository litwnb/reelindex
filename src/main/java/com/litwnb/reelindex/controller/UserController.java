package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.model.UserPrincipal;
import com.litwnb.reelindex.service.UserService;
import com.litwnb.reelindex.util.MovieErrorResponse;
import com.litwnb.reelindex.util.UsernameOccupiedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public UserPrincipal register(@RequestBody User user) {
        return new UserPrincipal(userService.register(user));
    }

    @GetMapping("/user")
    public UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal) authentication.getPrincipal();
    }

    @ExceptionHandler(UsernameOccupiedException.class)
    private ResponseEntity<MovieErrorResponse> handleException() {
        return new ResponseEntity<>(new MovieErrorResponse(
                "This user already exists",
                System.currentTimeMillis()
        ), HttpStatus.BAD_REQUEST);
    }
}
