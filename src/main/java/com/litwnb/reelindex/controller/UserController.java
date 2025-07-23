package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.mapper.UserMapper;
import com.litwnb.reelindex.model.ChangePasswordRequest;
import com.litwnb.reelindex.model.UserDTO;
import com.litwnb.reelindex.model.UserPrincipal;
import com.litwnb.reelindex.service.UserService;
import com.litwnb.reelindex.util.MovieErrorResponse;
import com.litwnb.reelindex.util.UsernameOccupiedException;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO newUser) {
        return ResponseEntity.ok(userService.register(newUser));
    }

    @GetMapping(USER_PATH)
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userMapper.userToUserDto(userPrincipal.getUser()));
    }

    @PostMapping(USER_PATH)
    public ResponseEntity<?> changeUserPassword(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userPrincipal.getUsername(),
                                   request.getCurrentPassword(),
                                   request.getNewPassword(),
                                   request.getConfirmNewPassword());
        return ResponseEntity.ok("Password successfully changed.");
    }

    @ExceptionHandler(UsernameOccupiedException.class)
    private ResponseEntity<MovieErrorResponse> handleException() {
        return new ResponseEntity<>(new MovieErrorResponse(
                "This user already exists",
                System.currentTimeMillis()
        ), HttpStatus.BAD_REQUEST);
    }
}
