package com.clush.test.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenizer {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    public final static Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; // 30분
    public final static Long REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 1000L; // 일주일

    public JwtTokenizer(@Value("${jwt.secretKey}") String accessSecret, @Value("${jwt.refreshKey}") String refreshSecret) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long id, String email, List<String> roles) {
        return createToken(id, email, roles, ACCESS_TOKEN_EXPIRE_COUNT, accessSecretKey);
    }

    public String createRefreshToken(Long id, String email, List<String> roles) {
        return createToken(id, email, roles, REFRESH_TOKEN_EXPIRE_COUNT, refreshSecretKey);
    }

    private String createToken(Long id, String email, List<String> roles, Long expire, SecretKey secretKey) {
        Claims claims = Jwts.claims().setSubject(email);

        claims.put("memberId", id);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expire))
                .signWith(secretKey)
                .compact();
    }

    public Long getMemberIdFromToken(String token) {
        String[] tokenArr = token.split(" ");
        token = tokenArr[1];
        Jws<Claims> claims = parseToken(token, accessSecretKey);
        return Long.valueOf((Integer) claims.getBody().get("memberId"));
    }

    public Jws<Claims> parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecretKey);
    }

    public Jws<Claims> parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecretKey);
    }

    private Jws<Claims> parseToken(String token, SecretKey secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}
