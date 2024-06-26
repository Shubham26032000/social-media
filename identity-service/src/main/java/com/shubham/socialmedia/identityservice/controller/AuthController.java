package com.shubham.socialmedia.identityservice.controller;

import com.shubham.socialmedia.identityservice.dto.AuthRequest;
import com.shubham.socialmedia.identityservice.entity.UserCredential;
import com.shubham.socialmedia.identityservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user){
        return authService.saveUser(user);
    }

    @GetMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        System.out.println(""+authRequest.getEmail()+" "+authRequest.getPassword());
        Authentication authentication  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        System.out.println("Not auth");
        if(authentication.isAuthenticated()) {
            System.out.println("Auth");
            return authService.generateToken(authRequest.getEmail());
        }else {

            System.out.println("End");
            throw new SecurityException("Invalid User");

        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return "Token is valid";
    }

}
