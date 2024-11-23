package com.hjhotelback.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 비밀 키 생성
    private final long EXPIRATION_MS = 86400000; // 1일 (밀리초 단위)

    // JWT 생성
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date()) // 토큰 생성 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // 만료 시간 설정
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512) // 서명 알고리즘과 비밀 키 설정
                .compact();
    }

    // JWT에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY) // 서명 키 설정
                .build() // JwtParser 생성
                .parseClaimsJws(token) // JWT 파싱
                .getBody();
        return claims.getSubject(); // 사용자 ID 반환
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY) // 서명 키 설정
                    .build() // JwtParser 생성
                    .parseClaimsJws(token); // JWT 파싱
            return true; // 유효한 토큰
        } catch (Exception e) {
            return false; // 유효하지 않은 토큰
        }
    }
}
