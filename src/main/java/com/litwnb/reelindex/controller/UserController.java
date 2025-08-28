package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.ChangePasswordRequest;
import com.litwnb.reelindex.model.UserDTO;
import com.litwnb.reelindex.model.UserPrincipal;
import com.litwnb.reelindex.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(newUser));
    }

    @GetMapping()
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.EXPIRES, "0")
                .body(userService.getUser(userPrincipal.getUsername()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changeUserPassword(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                     @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userPrincipal.getUsername(),
                                   request.getCurrentPassword(),
                                   request.getNewPassword(),
                                   request.getConfirmNewPassword());
        return ResponseEntity.ok("Password successfully changed.");
    }
}
