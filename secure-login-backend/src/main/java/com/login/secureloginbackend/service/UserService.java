package com.login.secureloginbackend.service;

import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import com.login.secureloginbackend.mapper.UserMapper;
import com.login.secureloginbackend.model.UserModel;
import com.login.secureloginbackend.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public TokenDTO login(LoginDTO loginDTO) {
        boolean existUser = userRepository.findByEmail(loginDTO.email());
        if (!existUser) {
            throw new RuntimeException("User not found");
        }
        //Validar la contraseña
        //Recuperar el token
        //Validar el token
        //Retornar el token
        return TokenDTO.builder().token("token").build();
    }

    public UserResponseDTO save(SignUpDTO user) {

        UserModel userModel = userMapper.fromUserDTO(user);
        userModel.setUserId(UUID.randomUUID());
        //Codificar la contraseña
        UserResponseDTO userResponseDTO = userMapper.fromUser(userRepository.save(userModel));
        userResponseDTO.setUserId(userModel.getUserId());

        return userResponseDTO;
    }

    public UserResponseDTO getUser(String email) {
        return userMapper.fromUser(userRepository.findUserModelByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));
    }

}
