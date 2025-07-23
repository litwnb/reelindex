package com.litwnb.reelindex.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 5, max = 20, message = "Password length should be between 5 and 20 characters")
    private String newPassword;

    @NotBlank(message = "Please confirm your new password")
    @Size(min = 5, max = 20, message = "Password length should be between 5 and 20 characters")
    private String confirmNewPassword;
}
