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
import com.login.secureloginbackend.util.PasswordEncodeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import io.jsonwebtoken.Claims;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final PasswordEncodeService encoder = new PasswordEncodeService();
    @Autowired
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;



    private boolean validateAdminExists(){
        Optional<UserModel> user = userRepository.findUserModelByRole(Role.ADMIN);
        return user.isPresent();
    }

    private Optional<UserModel> validateUserExists(String email){
        return userRepository.findUserModelByEmail(email);
    }

    public TokenDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.password()));
        UserDetails user = new SecurityUser(userRepository.findUserModelByEmail(loginDTO.email()).orElseThrow());
        String token = tokenService.generateToken(user);
        return TokenDTO.builder()
                .token(token)
                .email(loginDTO.email())
                .build();
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
        userModel.setPassword(encoder.encode(user.password()));

        //Guardar el usuario
        userRepository.save(userModel);

        return TokenDTO.builder()
                .token(tokenService.generateToken(new SecurityUser(userModel)))
                .email(userModel.getEmail())
                .build();
    }

    public UserResponseDTO getUser(String email) {
        return userMapper.fromUser(userRepository.findUserModelByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public boolean validateUserRole(String token){
        String userRole = tokenService.getClaim(token,Claims::getSubject);
        return userRole.equals("ADMIN");
    }

    public ResponseEntity<String> deleteUser(String email, String token) {
        //Para hacer esto necestita ser admin
        if (!validateUserRole(token)){
            throw new RuntimeException("User not authorized");
        }
        userRepository.delete(userRepository.findUserModelByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));
        return ResponseEntity.ok().body("User deleted");

    }

    private boolean validateUserOwner(String token, String email){
        String userEmail = tokenService.getClaim(token,Claims::getSubject);
        return userEmail.equals(email);
    }

    @Transactional
    public UserResponseDTO changePassword(ChangePasswordDTO changePasswordDTO, String token) {
        //Para cambiar la de cualquiera necestia ser admin

        if (!validateUserRole(token) && !validateUserOwner(token,changePasswordDTO.email())){
            //Si no es admin y tampoco es el propietario del usuario
            throw new RuntimeException("User not authorized");
        }

        Optional<UserModel> user = validateUserExists(changePasswordDTO.email());
        if(user.isEmpty()){
            throw new RuntimeException("User not found to change the password");
        }
        //Codificar la contraseña
        user.ifPresent(userModel -> userModel.setPassword(encoder.encode(changePasswordDTO.newPassword())));

        if (!validateUserRole(token) && validateUserOwner(token,changePasswordDTO.email())){
            //Si no es admin y es el propietario del usuario
            userRepository.updatePassword(changePasswordDTO.email(), user.get().getPassword());
        }

        if (validateUserRole(token)){
            //Si es admin
            userRepository.updatePassword(changePasswordDTO.email(), user.get().getPassword());
        }

        return userMapper.fromUser(user.get());
    }

    public List<UserResponseDTO> getAllUsers(String token){
        if(!validateUserRole(token)){
            throw new RuntimeException("User not authorized");
        }
        List<UserModel> list = userRepository.findAll();
        return list.stream().map(userMapper::fromUser).toList();
    }



}
