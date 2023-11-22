package com.login.secureloginbackend.config;

import com.login.secureloginbackend.service.security.UserManagementService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManager extends DaoAuthenticationProvider {

    public AuthenticationManager(UserManagementService userManagementService, PasswordEncoder passwordEncoder) {
        super();
        this.setUserDetailsService(userManagementService);
        this.setPasswordEncoder(passwordEncoder);
    }
}
