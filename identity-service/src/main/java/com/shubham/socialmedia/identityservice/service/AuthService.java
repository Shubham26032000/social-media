package com.shubham.socialmedia.identityservice.service;

import com.shubham.socialmedia.identityservice.entity.UserCredential;
import com.shubham.socialmedia.identityservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    public String saveUser(UserCredential credential){
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        credentialRepository.save(credential);
        return "User added to system";
    }

    public String generateToken(String username){
        return jwtService.generateTokenFromUsername(username);
    }

    public void validateToken(String token){
        jwtService.validateJwtToken(token);
    }
}
