package com.litwnb.reelindex.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private UUID id;

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 20, message = "Username length should be between 2 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 20, message = "Password length should be between 5 and 20 characters")
    private String password;
}
