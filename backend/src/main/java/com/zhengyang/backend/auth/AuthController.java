package com.zhengyang.backend.auth;

import com.zhengyang.backend.auth.dto.LoginResponse;
import com.zhengyang.backend.auth.dto.RegisterResponse;
import com.zhengyang.backend.auth.service.AuthCookieService;
import com.zhengyang.backend.auth.service.AuthService;
import com.zhengyang.backend.auth.service.RefreshTokenService;
import com.zhengyang.backend.auth.dto.LoginRequest;
import com.zhengyang.backend.auth.dto.RegisterRequest;
import com.zhengyang.backend.security.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
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
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    // get CSRF token
    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
    
    // register new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request,
                                      HttpServletRequest httpRequest,
                                      HttpServletResponse httpResponse) {
        try {
            RegisterResponse response = authService.register(request);
            String accessToken = jwtUtils.generateToken(response.getUsername());
            RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(response.getUsername());
            
            authCookieService.setAuthCookie(httpResponse, accessToken, httpRequest.isSecure());
            authCookieService.setRefreshCookie(httpResponse, refreshToken.getToken(), httpRequest.isSecure());
            
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
            String accessToken = jwtUtils.generateToken(response.getUsername());
            RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(response.getUsername());
            
            authCookieService.setAuthCookie(httpResponse, accessToken, httpRequest.isSecure());
            authCookieService.setRefreshCookie(httpResponse, refreshToken.getToken(), httpRequest.isSecure());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // refresh access token
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest httpRequest,
                                         HttpServletResponse httpResponse) {
        try {
            String refreshToken = authCookieService.extractRefreshCookie(httpRequest)
                .orElseThrow(() -> new RuntimeException("Refresh token is missing"));
            
            String username = authService.refreshAccessToken(refreshToken);
            String newAccessToken = jwtUtils.generateToken(username);
            
            authCookieService.setAuthCookie(httpResponse, newAccessToken, httpRequest.isSecure());
            
            return ResponseEntity.ok().body("Token refreshed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // logout user
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, HttpServletRequest request) {
        authCookieService.extractRefreshCookie(request)
            .ifPresent(refreshTokenService::deleteByToken);
        
        authCookieService.clearAuthCookie(response, request.isSecure());
        authCookieService.clearRefreshCookie(response, request.isSecure());
        
        return ResponseEntity.ok().build();
    }

}
