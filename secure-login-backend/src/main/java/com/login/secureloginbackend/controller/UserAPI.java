package com.login.secureloginbackend.controller;

import com.login.secureloginbackend.dto.request.ChangePasswordDTO;
import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/auth")
public interface UserAPI {


    @PostMapping()
    TokenDTO login(@Valid @RequestBody LoginDTO loginDTO);

    @PostMapping("/signup")
    TokenDTO signUp(@Valid @RequestBody SignUpDTO signUpDTO);

    @GetMapping("/user/{email}")
    UserResponseDTO getUser(@PathVariable String email);

    @DeleteMapping("/user/{email}")
    ResponseEntity<String> deleteUser(@PathVariable String email);

    @PatchMapping("/user/changePassword")
    UserResponseDTO changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO);

    @GetMapping("/users")
    List<UserResponseDTO> getAllUsers();


}
