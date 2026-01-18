package com.zhengyang.backend.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthCookieService {

    public static final String AUTH_COOKIE = "auth_token";

    public void setAuthCookie(HttpServletResponse response, String token, boolean secure) {
        ResponseCookie cookie = ResponseCookie.from(AUTH_COOKIE, token)
            .httpOnly(true)
            .secure(secure)
            .path("/")
            .sameSite("None")
            .maxAge(Duration.ofDays(7))
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void clearAuthCookie(HttpServletResponse response, boolean secure) {
        ResponseCookie cookie = ResponseCookie.from(AUTH_COOKIE, "")
            .httpOnly(true)
            .secure(secure)
            .path("/")
            .sameSite("None")
            .maxAge(Duration.ZERO)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public Optional<String> extractCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }

        return Arrays.stream(request.getCookies())
            .filter(c -> AUTH_COOKIE.equals(c.getName()))
            .map(Cookie::getValue)
            .findFirst();
    }
}
