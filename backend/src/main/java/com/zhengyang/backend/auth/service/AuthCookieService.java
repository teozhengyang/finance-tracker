package com.zhengyang.backend.auth.service;

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
    public static final String REFRESH_COOKIE = "refresh_token";

    public void setAuthCookie(HttpServletResponse response, String token, boolean secure) {
        ResponseCookie cookie = ResponseCookie.from(AUTH_COOKIE, token)
            .httpOnly(true)
            .secure(secure)
            .path("/")
            .sameSite("Strict")
            .maxAge(Duration.ofDays(1))
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void setRefreshCookie(HttpServletResponse response, String token, boolean secure) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_COOKIE, token)
            .httpOnly(true)
            .secure(secure)
            .path("/api/auth")
            .sameSite("Strict")
            .maxAge(Duration.ofDays(7))
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void clearAuthCookie(HttpServletResponse response, boolean secure) {
        ResponseCookie cookie = ResponseCookie.from(AUTH_COOKIE, "")
            .httpOnly(true)
            .secure(secure)
            .path("/")
            .sameSite("Strict")
            .maxAge(Duration.ZERO)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void clearRefreshCookie(HttpServletResponse response, boolean secure) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_COOKIE, "")
            .httpOnly(true)
            .secure(secure)
            .path("/api/auth")
            .sameSite("Strict")
            .maxAge(Duration.ZERO)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public Optional<String> extractCookie(HttpServletRequest request) {
        return extractCookie(request, AUTH_COOKIE);
    }

    public Optional<String> extractRefreshCookie(HttpServletRequest request) {
        return extractCookie(request, REFRESH_COOKIE);
    }

    private Optional<String> extractCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }

        return Arrays.stream(request.getCookies())
            .filter(c -> cookieName.equals(c.getName()))
            .map(Cookie::getValue)
            .findFirst();
    }
}
