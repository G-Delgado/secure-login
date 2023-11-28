package com.login.secureloginbackend.controller;

import com.login.secureloginbackend.dto.request.ChangePasswordDTO;
import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 */
@RequestMapping("/auth")
public interface UserAPI {

    /**
     * @param loginDTO - DTO with the email and password of the user
     * @return - TokenDTO with the token and email of the user
     */
    @PostMapping("/login")
    TokenDTO login(@Valid @RequestBody LoginDTO loginDTO);

    /**
     * @param signUpDTO - DTO with the email and password of the user
     * @return - TokenDTO with the token and email of the user
     */
    @PostMapping("/signup")
    TokenDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO);

    /**
     * @param email - email of the user
     * @return - UserResponseDTO with the information of the user
     */
    @GetMapping("/user/{email}")
    ResponseEntity<UserResponseDTO> getUser(@PathVariable String email);

    /**
     * @param email - email of the user
     * @param token - token of the user
     * @return - String with the message of the operation
     */
    @DeleteMapping("/user/{email}")
    ResponseEntity<String> deleteUser(@PathVariable String email,@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * @param changePasswordDTO - DTO with the email, old and new password of the user
     * @param token - token of the user
     * @return - UserResponseDTO with the information of the user
     */
    @PatchMapping("/user/changePassword")
    UserResponseDTO changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO,@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * @param token - token of the admin
     * @return - List of UserResponseDTO with the information of the users
     */
    @GetMapping("/users")
    List<UserResponseDTO> getAllUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * @param token - token of the user
     * @return - Boolean with the confirmation whether the user is admin
     */
    @GetMapping("/role")
    ResponseEntity<Boolean> isAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * @param token - token of the admin
     * @return - Long with the number of users
     */
    @GetMapping("/count")
    ResponseEntity<Long> countUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}

