package com.login.secureloginbackend.service;

import com.login.secureloginbackend.dto.request.ChangePasswordDTO;
import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import com.login.secureloginbackend.mapper.UserMapper;
import com.login.secureloginbackend.model.Role;
import com.login.secureloginbackend.model.SecurityUser;
import com.login.secureloginbackend.model.UserModel;
import com.login.secureloginbackend.repository.UserRepository;
import com.login.secureloginbackend.service.security.TokenService;
import lombok.AllArgsConstructor;

import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.login.secureloginbackend.util.PasswordEncodeService;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    private final TokenService tokenService;



    private boolean validateAdminExists(){
        return userRepository.findUserModelAdmin().isPresent();
    }

    private Optional<UserModel> validateUserExists(String email){
        return userRepository.findUserModelByEmail(email);
    }

    public TokenDTO login(LoginDTO loginDTO) {
        Optional<UserModel> existUser = validateUserExists(loginDTO.email());
        if (existUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

            //Validar la contraseña
        try {
            if(!PasswordEncodeService.passwordVerify(loginDTO.password(), existUser.get().getPassword())){
                throw new RuntimeException("Password incorrect");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        //Recuperar el token
        //Validar el token
        //Retornar el token
        //Actualizar el lastLogin


        return TokenDTO.builder().token("token").build();
    }

    public TokenDTO save(SignUpDTO user) {
        boolean existUser = validateUserExists(user.email()).isPresent();
        if (existUser) {
            throw new RuntimeException("User already exists");
        }

        UserModel userModel = userMapper.fromUserDTO(user);
        userModel.setUserId(UUID.randomUUID());
        userModel.setRole(validateAdminExists()? Role.USER : Role.ADMIN);
        userModel.setLastLogin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        //Codificar la contraseña
        try {
            userModel.setPassword(PasswordEncodeService.encodePassword(user.password()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        userRepository.save(userModel);

        return TokenDTO.builder()
                .token(tokenService.generateToken(new SecurityUser(userModel)))
                .email(userModel.getEmail())
                .build();
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
