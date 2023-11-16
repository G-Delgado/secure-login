package com.login.secureloginbackend.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponseDTO(UUID userId,
                              String firstName,
                              String lastName,
                              String email,
                              String password,
                              String phoneNumber) {


}
