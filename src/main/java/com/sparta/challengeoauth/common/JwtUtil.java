package com.sparta.challengeoauth.common;

import com.sparta.challengeoauth.domain.user.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String accessTokenSecretKey;

    private long accessTokenExpiresIn = 60 * 60 * 1000L; // 60분

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        return "Bearer " + Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiresIn))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validate(String accessToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
        } catch (JwtException e) {
            throw new JwtException("잘못된 엑세스 토큰입니다.", e);
        }
    }

    public Long extractUserId(String accessToken) {
        try {
            String subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getSubject();
            return Long.valueOf(subject);
        } catch (Exception e) {
            throw new JwtException("잘못된 엑세스 토큰입니다.", e);
        }
    }
}
