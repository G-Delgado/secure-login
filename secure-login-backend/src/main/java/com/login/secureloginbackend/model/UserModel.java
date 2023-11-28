package com.login.secureloginbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

//@Data
@Entity
@Table(name = "SEC_USER")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "USER_FIRST_NAME")
    private String firstName;

    @Column(name = "USER_LAST_NAME")
    private String lastName;

    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_ROLE")
    private Role role;

    @Column(name = "USER_LAST_LOGIN")
    private String lastLogin;


}
