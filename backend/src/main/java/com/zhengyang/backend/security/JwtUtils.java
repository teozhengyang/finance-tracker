package com.zhengyang.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${jwt.refresh.expiration}")
    private long jwtRefreshExpirationMs;

    // get signing key from secret
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // extract claims from JWT token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .setAllowedClockSkewSeconds(30)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // generate JWT token for username
    public String generateToken(String username) {
        return generateTokenWithExpiration(username, jwtExpirationMs);
    }

    // generate refresh token for username
    public String generateRefreshToken(String username) {
        return generateTokenWithExpiration(username, jwtRefreshExpirationMs);
    }

    // generate token with custom expiration
    private String generateTokenWithExpiration(String username, long expirationMs) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // get username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // get expiration time in milliseconds
    public long getRefreshExpirationMs() {
        return jwtRefreshExpirationMs;
    }
}
