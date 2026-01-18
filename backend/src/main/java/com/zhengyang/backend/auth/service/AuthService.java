package com.zhengyang.backend.auth.service;

import com.zhengyang.backend.auth.dto.LoginResponse;
import com.zhengyang.backend.auth.dto.RegisterResponse;
import com.zhengyang.backend.auth.RefreshTokenEntity;
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
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
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
        return new LoginResponse(user.getUsername(), user.getEmail(), user.isAdmin());
    }
    
    // refresh access token using refresh token
    public String refreshAccessToken(String refreshToken) {
        RefreshTokenEntity token = refreshTokenService.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));
        
        refreshTokenService.verifyExpiration(token);
        
        return token.getUser().getUsername();
    }
}
