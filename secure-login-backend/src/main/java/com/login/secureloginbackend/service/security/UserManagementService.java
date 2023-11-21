package com.login.secureloginbackend.service.security;

import com.login.secureloginbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.login.secureloginbackend.model.SecurityUser;

@Service
@AllArgsConstructor
public class UserManagementService implements UserDetailsService {

    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserModelByEmail(username).map(SecurityUser::new).orElseThrow(()->new UsernameNotFoundException("User not found: "+ username));
    }
}
