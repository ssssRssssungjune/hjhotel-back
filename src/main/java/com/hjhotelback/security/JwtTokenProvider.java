package com.hjhotelback.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey SECRET_KEY;
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    public JwtTokenProvider() {
        // HS256 알고리즘을 위한 키 생성
        this.SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.RS256);
    }

    // JWT 생성
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId) // 사용자 ID 설정
                .setIssuedAt(new Date()) // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(SECRET_KEY) // 서명
                .compact();
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY) // 서명 검증 키 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 검증 및 Claims 파싱
            return true;
        } catch (JwtException e) {
            return false; // 서명 검증 실패
        }
    }

    // JWT에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // 서명 검증 키 설정
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // subject 필드 반환
    }
}
