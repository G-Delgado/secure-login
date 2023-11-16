package com.login.secureloginbackend.controller;

import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/auth")
public interface UserAPI {


    @PostMapping("/login")
    TokenDTO login(@Valid @RequestBody LoginDTO loginDTO);

    @PostMapping("/signup")
    UserResponseDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO);

    @GetMapping("/user/{email}")
    UserResponseDTO getUser(@PathVariable String email);

    @DeleteMapping("/user/{email}")
    void deleteUser(@PathVariable String email);

    @GetMapping("/users")
    void getAllUsers();
}
