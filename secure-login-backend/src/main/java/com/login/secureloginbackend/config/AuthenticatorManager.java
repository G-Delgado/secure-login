package com.login.secureloginbackend.config;

import com.login.secureloginbackend.model.SecurityUser;
import com.login.secureloginbackend.security.CustomAuthentication;
import com.login.secureloginbackend.service.security.UserManagementService;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;



@Component
public class AuthenticatorManager extends DaoAuthenticationProvider{

    public AuthenticatorManager(UserManagementService userManagementService) {
        super();
        this.setUserDetailsService(userManagementService);

    }

    @Override
    public Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user){
        UsernamePasswordAuthenticationToken successAuth = (UsernamePasswordAuthenticationToken) super.createSuccessAuthentication(principal, authentication, user);
        SecurityUser securityUser = (SecurityUser) user;
        return new CustomAuthentication(successAuth, securityUser.userModel().getUserId().toString(),securityUser.userModel().getEmail().toString());
    }
}
