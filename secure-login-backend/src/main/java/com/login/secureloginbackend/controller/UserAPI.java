package com.login.secureloginbackend.controller;

import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/auth")
public interface UserAPI {


    @PostMapping("/login")
    UserResponseDTO login(@Valid @RequestBody LoginDTO loginDTO);

    @PostMapping("/signup")
    UserResponseDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO);

    @GetMapping("/user/{email}")
    UserResponseDTO getUser();

    @DeleteMapping("/user/{email}")
    void deleteUser();

    @GetMapping("/users")
    void getAllUsers();
}
