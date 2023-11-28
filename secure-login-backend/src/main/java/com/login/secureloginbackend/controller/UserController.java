package com.login.secureloginbackend.controller;

import com.login.secureloginbackend.dto.request.ChangePasswordDTO;
import com.login.secureloginbackend.dto.request.LoginDTO;
import com.login.secureloginbackend.dto.request.SignUpDTO;
import com.login.secureloginbackend.dto.response.TokenDTO;
import com.login.secureloginbackend.dto.response.UserResponseDTO;
import com.login.secureloginbackend.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {
    private final UserService userService;

    /**
     * Método que permite loguearse
     * @param loginDTO - DTO con los datos de login
     * @return - TokenDTO con el token generado
     */
    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    /**
     * Método que permite registrarse
     * @param signUpDTO - DTO con los datos de registro
     * @return - TokenDTO con el token generado
     */
    @Override
    public TokenDTO signUp(SignUpDTO signUpDTO) {
        return userService.save(signUpDTO);
    }

    /**
     * Método que permite obtener un usuario
     * @param email - email del usuario
     * @return - UserResponseDTO con los datos del usuario
     */
    @Override
    public ResponseEntity<UserResponseDTO> getUser(String email) {
        return ResponseEntity.ok(userService.getUser(email));
    }

    /**
     * Método que permite actualizar un usuario
     * @param userResponseDTO - DTO con los datos del usuario
     * @param token - token del usuario
     * @return - UserResponseDTO con los datos del usuario
     */
    @Override
    public ResponseEntity<String> deleteUser(String email, String token) {
        //Necesita ser admin
        return userService.deleteUser(email,token);
    }

    /**
     * Método que permite actualizar un usuario
     * @param userResponseDTO - DTO con los datos del usuario
     * @param token - token del usuario
     * @return - UserResponseDTO con los datos del usuario
     */
    @Override
    public UserResponseDTO changePassword(ChangePasswordDTO changePasswordDTO, String token) {
        //Necesita ser admin para cambiar la contraseña de cualquiera
        return userService.changePassword(changePasswordDTO,token);

    }

    /**
     * Método que permite actualizar un usuario
     * @param userResponseDTO - DTO con los datos del usuario
     * @param token - token del usuario
     * @return - UserResponseDTO con los datos del usuario
     */
    @Override
    public List<UserResponseDTO> getAllUsers(String token) {
        //Necesita ser admin
        return userService.getAllUsers(token);

    }

    /**
     * Método que permite actualizar un usuario
     * @param userResponseDTO - DTO con los datos del usuario
     * @param token - token del usuario
     * @return - UserResponseDTO con los datos del usuario
     */
    @Override
    public ResponseEntity<Boolean> isAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(userService.validateUserRoleIsAdmin(token));
    }

    /**
     * Método que permite actualizar un usuario
     * @param userResponseDTO - DTO con los datos del usuario
     * @param token - token del usuario
     * @return - UserResponseDTO con los datos del usuario
     */
    @Override
    public ResponseEntity<Long> countUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(userService.countUsers(token));
    }


}
