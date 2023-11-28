package com.login.secureloginbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * This class is a DTO that represents the response of the user
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
public class UserResponseDTO {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String lastLogin;


}
