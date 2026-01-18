package com.zhengyang.backend.auth;

import com.zhengyang.backend.auth.dto.LoginResponse;
import com.zhengyang.backend.auth.dto.RegisterResponse;
import com.zhengyang.backend.auth.dto.LoginRequest;
import com.zhengyang.backend.auth.dto.RegisterRequest;
import com.zhengyang.backend.user.UserEntity;
import com.zhengyang.backend.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    // register new user
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        user = userRepository.save(user);
        
        return new RegisterResponse(
                       user.getUsername(), user.getEmail(), user.isAdmin());
    }
    
    // login user
    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // token is generated in controller (set as cookie); response body should not include token/id
        return new LoginResponse(user.getUsername(), user.getEmail(), user.isAdmin());
    }
}
