package com.hjhotelback.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey key;
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간 (밀리초)

    @PostConstruct
    protected void init() {
        // Base64로 인코딩된 시크릿 키를 디코딩하여 바이트 배열로 변환
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        // 바이트 배열로부터 SecretKey 객체 생성
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 생성
    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Claims 추출 부분 수정 (JJWT 최신 버전 대응)
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
