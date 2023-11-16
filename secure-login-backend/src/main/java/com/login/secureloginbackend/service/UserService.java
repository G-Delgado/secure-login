package com.login.secureloginbackend.service;

import com.login.secureloginbackend.dto.request.ChangePasswordDTO;
import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import com.login.secureloginbackend.mapper.UserMapper;
import com.login.secureloginbackend.model.UserModel;
import com.login.secureloginbackend.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    private boolean validateAdminExists(){
        return userRepository.findUserModelAdmin().isPresent();
    }

    private boolean validateIsAdmin(String email){
        Optional<UserModel> user =  userRepository.findUserModelByEmail(email);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return user.get().isAdmin();
    }

    private Optional<UserModel> validateUserExists(String email){
        Optional<UserModel> user = userRepository.findUserModelByEmail(email);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return user;
    }

    public TokenDTO login(LoginDTO loginDTO) {
        boolean existUser = validateUserExists(loginDTO.email()).isPresent();

        if(existUser){
            //Validar la contraseña
            //Recuperar el token
            //Validar el token
            //Retornar el token
        }

        return TokenDTO.builder().token("token").build();
    }

    public UserResponseDTO save(SignUpDTO user) {
        boolean existUser = validateUserExists(user.email()).isPresent();
        if (existUser) {
            throw new RuntimeException("User already exists");
        }

        UserModel userModel = userMapper.fromUserDTO(user);
        userModel.setUserId(UUID.randomUUID());
        userModel.setAdmin(!validateAdminExists());
        //Codificar la contraseña
        UserResponseDTO userResponseDTO = userMapper.fromUser(userRepository.save(userModel));
        userResponseDTO.setUserId(userModel.getUserId());

        return userResponseDTO;
    }

    public UserResponseDTO getUser(String email) {
        return userMapper.fromUser(userRepository.findUserModelByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public ResponseEntity<String> deleteUser(String email) {
        //Para hacer esto necestita ser admin
        userRepository.delete(userRepository.findUserModelByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));

        return ResponseEntity.ok().body("User deleted");

    }

    @Transactional
    public UserResponseDTO changePassword(ChangePasswordDTO changePasswordDTO) {
        //Para cambiar la de cualquiera necestia ser admin
        //Hay que validar si quien mando la request es admin, sino solo puede cambiar su contraseña
        Optional<UserModel> user = validateUserExists(changePasswordDTO.email());
        if(user.isEmpty()){
            throw new RuntimeException("User not found to change the password");
        }
        //Codificar la contraseña
        user.ifPresent(userModel -> userModel.setPassword(changePasswordDTO.newPassword()));
        userRepository.updatePassword(changePasswordDTO.email(), user.get().getPassword());

        return userMapper.fromUser(user.get());
    }

    public List<UserResponseDTO> getAllUsers(){
        //Para hacer esto necestita ser admin
        List<UserModel> list = userRepository.findAll();
        return list.stream().map(userMapper::fromUser).toList();
    }



}
