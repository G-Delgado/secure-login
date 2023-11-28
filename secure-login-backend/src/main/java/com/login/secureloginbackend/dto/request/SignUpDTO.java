package com.login.secureloginbackend.dto.request;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This class is a DTO that represents the request of the sign up
 * @param firstName
 * @param lastName
 * @param email
 * @param password
 */
@Builder
public record SignUpDTO(
        @NotNull
        @NotBlank
        String firstName,

        @NotNull
        @NotBlank
        String lastName,

        @NotNull
        @NotBlank
        @Email
        String email,

        @NotNull
        @NotBlank
        String password
) {
}
