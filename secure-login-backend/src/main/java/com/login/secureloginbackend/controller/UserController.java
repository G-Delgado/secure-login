package com.login.secureloginbackend.controller;

import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import com.login.secureloginbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {
    private final UserService userService;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @Override
    public UserResponseDTO signUp(SignUpDTO signUpDTO) {
        return userService.save(signUpDTO);
    }

    @Override
    public UserResponseDTO getUser(String email) {
        return userService.getUser(email);
    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public void getAllUsers() {

    }
}
