package com.login.secureloginbackend.controller;

import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import com.login.secureloginbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {
    private final UserService userService;

    @Override
    public UserResponseDTO login(LoginDTO loginDTO) {
        return null;
    }

    @Override
    public UserResponseDTO signUp(SignUpDTO signUpDTO) {
        return null;
    }

    @Override
    public UserResponseDTO getUser() {
        return null;
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void getAllUsers() {

    }
}
