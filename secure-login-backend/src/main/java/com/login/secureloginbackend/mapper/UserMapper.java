package com.login.secureloginbackend.mapper;


import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import com.login.secureloginbackend.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserModel fromLoginDTO(LoginDTO loginDTO);
    UserModel fromUserDTO(SignUpDTO signUpDTO);
    UserResponseDTO fromUser(UserModel userModel);

}
