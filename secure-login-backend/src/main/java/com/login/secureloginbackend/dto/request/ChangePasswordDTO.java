package com.login.secureloginbackend.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO for changing the password
 * @param email
 * @param oldPassword
 * @param newPassword
 */
@Builder
public record ChangePasswordDTO(
        @NotBlank
        String email,
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword
) {
}
