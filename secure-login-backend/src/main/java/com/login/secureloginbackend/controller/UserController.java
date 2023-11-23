package com.login.secureloginbackend.controller;

import com.login.secureloginbackend.dto.request.ChangePasswordDTO;
import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import com.login.secureloginbackend.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {
    private final UserService userService;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @Override
    public TokenDTO signUp(SignUpDTO signUpDTO) {
        return userService.save(signUpDTO);
    }

    @Override
    public UserResponseDTO getUser(String email) {
        return userService.getUser(email);
    }

    @Override
    public ResponseEntity<String> deleteUser(String email, String token) {
        //Necesita ser admin
        return userService.deleteUser(email,token);
    }

    @Override
    public UserResponseDTO changePassword(ChangePasswordDTO changePasswordDTO, String token) {
        //Necesita ser admin para cambiar la contraseña de cualquiera
        return userService.changePassword(changePasswordDTO,token);

    }

    @Override
    public List<UserResponseDTO> getAllUsers(String token) {
        //Necesita ser admin
        return userService.getAllUsers(token);

    }


}
