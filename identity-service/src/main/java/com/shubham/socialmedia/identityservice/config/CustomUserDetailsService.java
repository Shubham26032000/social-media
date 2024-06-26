package com.shubham.socialmedia.identityservice.config;

import com.shubham.socialmedia.identityservice.entity.UserCredential;
import com.shubham.socialmedia.identityservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserCredential> userOptional = repository.findByEmail(email);
        return userOptional.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name ....!"+email));
    }
}
