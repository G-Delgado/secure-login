package com.login.secureloginbackend.service.security;


import com.login.secureloginbackend.repository.UserRepository;
import com.login.secureloginbackend.model.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserManagementService implements UserDetailsService {

    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findUserModelByEmail(email).map(SecurityUser::new).orElseThrow(()->new UsernameNotFoundException("User not found: "+ email));
    }
}
