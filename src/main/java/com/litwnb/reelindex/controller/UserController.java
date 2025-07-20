package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.mapper.UserMapper;
import com.litwnb.reelindex.model.UserDTO;
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
    private final UserMapper userMapper;

    @PostMapping(REGISTER_PATH)
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        return ResponseEntity.ok(userMapper.userToUserDto(userService.register(user)));
    }

    @GetMapping(USER_PATH)
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userMapper.userToUserDto(userPrincipal.getUser()));
    }

    @ExceptionHandler(UsernameOccupiedException.class)
    private ResponseEntity<MovieErrorResponse> handleException() {
        return new ResponseEntity<>(new MovieErrorResponse(
                "This user already exists",
                System.currentTimeMillis()
        ), HttpStatus.BAD_REQUEST);
    }
}
