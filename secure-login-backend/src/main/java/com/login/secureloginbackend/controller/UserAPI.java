package com.login.secureloginbackend.controller;

import com.login.secureloginbackend.dto.request.ChangePasswordDTO;
import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/auth")
public interface UserAPI {


    @PostMapping("/login")
    TokenDTO login(@Valid @RequestBody LoginDTO loginDTO);


    @PostMapping("/signup")
    TokenDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO);

    @GetMapping("/user/{email}")
    UserResponseDTO getUser(@PathVariable String email);

    @DeleteMapping("/user/{email}")
    ResponseEntity<String> deleteUser(@PathVariable String email,@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PatchMapping("/user/changePassword")
    UserResponseDTO changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO,@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("/users")
    List<UserResponseDTO> getAllUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("/role")
    ResponseEntity<Boolean> isAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}

