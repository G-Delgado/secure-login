package com.login.secureloginbackend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class CustomAuthentication implements Authentication{
    private final Authentication authentication;
    private final String userId;
    private final String userEmail;
    public CustomAuthentication(Authentication authentication, String userId, String userEmail){
        this.authentication = authentication;
        this.userId = userId;
        this.userEmail = userEmail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authentication.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return authentication.getCredentials();
    }

    @Override
    public Object getDetails() {
        return authentication.getDetails();
    }

    @Override
    public Object getPrincipal() {
        return authentication.getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        return authentication.isAuthenticated();
    }
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authentication.setAuthenticated(isAuthenticated);
    }

    @Override
    public String getName() {
        return userEmail;
    }

    @Override
    public boolean implies(Subject subject) {
        return authentication.implies(subject);
    }
    public String getUserId(){
        return userId;
    }
}
