package com.zhengyang.backend.controller;

import com.zhengyang.backend.dto.AuthResponse;
import com.zhengyang.backend.dto.LoginRequest;
import com.zhengyang.backend.dto.RegisterRequest;
import com.zhengyang.backend.entity.User;
import com.zhengyang.backend.repository.UserRepository;
import com.zhengyang.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
    
    // Protected endpoint - requires authentication
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return ResponseEntity.ok(user);
    }
    
    // Protected endpoint - requires authentication
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(Authentication authentication) {
        return ResponseEntity.ok("Welcome to your dashboard, " + authentication.getName() + "!");
    }
}
