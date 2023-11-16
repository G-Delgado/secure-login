package com.login.secureloginbackend.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

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
