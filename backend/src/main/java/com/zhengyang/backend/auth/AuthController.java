package com.zhengyang.backend.auth;

import com.zhengyang.backend.auth.dto.LoginResponse;
import com.zhengyang.backend.auth.dto.RegisterResponse;
import com.zhengyang.backend.auth.dto.LoginRequest;
import com.zhengyang.backend.auth.dto.RegisterRequest;
import com.zhengyang.backend.security.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthCookieService authCookieService;
    
    // register new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request,
                                      HttpServletRequest httpRequest,
                                      HttpServletResponse httpResponse) {
        try {
            RegisterResponse response = authService.register(request);
            String token = jwtUtils.generateToken(response.getUsername());
            authCookieService.setAuthCookie(httpResponse, token, httpRequest.isSecure());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // login user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
                                   HttpServletRequest httpRequest,
                                   HttpServletResponse httpResponse) {
        try {
            LoginResponse response = authService.login(request);
            String token = jwtUtils.generateToken(response.getUsername());
            authCookieService.setAuthCookie(httpResponse, token, httpRequest.isSecure());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    // logout user
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, HttpServletRequest request) {
        authCookieService.clearAuthCookie(response, request.isSecure());
        return ResponseEntity.ok().build();
    }

}
