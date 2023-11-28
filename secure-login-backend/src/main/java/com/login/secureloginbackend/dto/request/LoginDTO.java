package com.login.secureloginbackend.dto.request;


import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This class is a DTO that represents the request of the login
 * @param email
 * @param password
 */
@Builder
public record LoginDTO(
        @NotNull
        @NotBlank
        @Email
        String email,
        @NotNull
        @NotBlank
        String password
) {
}
