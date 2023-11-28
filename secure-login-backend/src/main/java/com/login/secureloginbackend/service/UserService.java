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

    /**
     * Validates if the admin exists in the database
     * @return - Boolean with the result of the operation
     */
    private boolean validateAdminExists(){
        Optional<UserModel> user = userRepository.findUserModelByRole(Role.ADMIN);
        return user.isPresent();
    }

    /**
     * Validates if the user exists in the database
     * @param email - email of the user
     * @return - Optional of UserModel with the user
     */
    private Optional<UserModel> validateUserExists(String email){
        return userRepository.findUserModelByEmail(email);
    }

    /**
     * Handles the login of the user
     * @param loginDTO - DTO with the email and password of the user
     * @return - TokenDTO with the token and email of the user
     */
    public TokenDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.password()));
        UserDetails user = new SecurityUser(userRepository.findUserModelByEmail(loginDTO.email()).orElseThrow());
        String token = tokenService.generateToken(user);
        return TokenDTO.builder()
                .token(token)
                .email(loginDTO.email())
                .build();
    }

    /**
     * Handles the sign up of the user
     * @param user - DTO with the email and password of the user
     * @return - TokenDTO with the token and email of the user
     */
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

    /**
     * Handles the request for an user information
     * @param email - email of the user
     * @return - UserResponseDTO with the information of the user
     */
    public UserResponseDTO getUser(String email) {
        return userMapper.fromUser(userRepository.findUserModelByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));
    }

    /**
     * Handles the request for the numbers of users
     * @param token - token of the admin
     * @return - Long with the amount of users
     */
    public Long countUsers(String token){
        if(!validateAdminRole(token.split(" ")[1].trim())){
            throw new RuntimeException("User not authorized");
        }
        return userRepository.count();
    }

    /**
     * Validates the role of the user
     * @param token - token of the user
     * @return - Boolean with the confirmation whether the user is admin
     */
    private boolean validateUserRole(String token){
        String userRole = tokenService.getClaim(token,Claims::getSubject);
        return userRole.equals("ADMIN");
    }

    /**
     * Validates the role of the user
     * @param token - token of the user
     * @return - Boolean with the confirmation whether the user is admin
     */
    private boolean validateAdminRole(String token){
        System.out.println(token);
        Object userRole = tokenService.getRole(token,Claims::getSubject);
        System.out.println((String) userRole.toString());
        return userRole.toString().equals("ADMIN");
    }

    /**
     * Validates the role of the user
     * @param token - token of the user
     * @return - Boolean with the confirmation whether the user is admin
     */
    public Boolean validateUserRoleIsAdmin(String token){
        UserModel user = userRepository.findUserModelByRole(Role.ADMIN).orElseThrow(() -> new RuntimeException("Admin not found"));
        System.out.println(token.split(" ")[1].trim());
        String email = tokenService.extractUsername(token.split(" ")[1].trim());
        return user.getEmail().equals(email);
    }

    /**
     * Handles the request for the role of the user
     * @param token - token of the user
     * @return - Boolean with the confirmation whether the user is admin
     */
    public ResponseEntity<String> deleteUser(String email, String token) {
        //Para hacer esto necestita ser admin
        if (!validateAdminRole(token.split(" ")[1].trim())){
            throw new RuntimeException("User not authorized");
        }
        userRepository.delete(userRepository.findUserModelByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));
        return ResponseEntity.ok().body("User deleted");

    }

    /**
     * Validates if the user is the owner of the token
     * @param token - token of the user
     * @return - Boolean with the confirmation whether the user is the owner of the token
     */
    private boolean validateUserOwner(String token, String email){
        String userEmail = tokenService.getClaim(token,Claims::getSubject);
        System.out.println("Validando dueño");
        System.out.println(userEmail);
        return userEmail.equals(email);
    }

    /**
     * Handles the request for the change of the password of the user
     * @param changePasswordDTO - DTO with the email, old and new password of the user
     * @param token - token of the user
     * @return - UserResponseDTO with the information of the user
     */
    @Transactional
    public UserResponseDTO changePassword(ChangePasswordDTO changePasswordDTO, String token) {
        //Para cambiar la de cualquiera necestia ser admin
        System.out.println("Entra?");
        token = token.split(" ")[1].trim();
        if (!validateAdminRole(token) && !validateUserOwner(token,changePasswordDTO.email())){
            //Si no es admin y tampoco es el propietario del usuario
            throw new RuntimeException("User not authorized");
        }

        Optional<UserModel> user = validateUserExists(changePasswordDTO.email());
        if(user.isEmpty()){
            throw new RuntimeException("User not found to change the password");
        }
        //Codificar la contraseña
        user.ifPresent(userModel -> userModel.setPassword(encoder.encode(changePasswordDTO.newPassword())));

        if (!validateAdminRole(token) && validateUserOwner(token,changePasswordDTO.email())){
            //Si no es admin y es el propietario del usuario
            userRepository.updatePassword(changePasswordDTO.email(), user.get().getPassword());
        }

        if (validateAdminRole(token)){
            //Si es admin
            userRepository.updatePassword(changePasswordDTO.email(), user.get().getPassword());
        }

        return userMapper.fromUser(user.get());
    }


    /**
     * Handles the request for the list of users
     * @param token - token of the admin
     * @return - List of UserResponseDTO with the information of the users
     */
    public List<UserResponseDTO> getAllUsers(String token){
        if(!validateAdminRole(token.split(" ")[1].trim())){
            throw new RuntimeException("User not authorized");
        }
        List<UserModel> list = userRepository.findAll();
        return list.stream().map(userMapper::fromUser).toList();
    }



}
