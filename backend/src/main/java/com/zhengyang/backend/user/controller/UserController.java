package com.zhengyang.backend.user.controller;

import com.zhengyang.backend.user.dto.UserResponse;
import com.zhengyang.backend.user.UserEntity;
import com.zhengyang.backend.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    // get user profile
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(Authentication authentication) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserResponse response = new UserResponse(
            user.getUsername(),
            user.getEmail(),
            user.isAdmin()
        );
        
        return ResponseEntity.ok(response);
    }
}
