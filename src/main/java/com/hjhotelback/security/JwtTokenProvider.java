package com.hjhotelback.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public String createToken(String userId) {
        long now = System.currentTimeMillis();
        Date validity = new Date(now + this.tokenValidityInSeconds * 1000);

        return Jwts.builder()
                // Header
                .signWith(key, SignatureAlgorithm.HS512)
                // Payload
                // -- Registered Claims
                .setSubject(userId) // userId를 토큰의 subject로 설정
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .compact();
    }
    // JWT 생성 (generateToken 호출 추가)
    public String generateToken(String userId) {
        return createToken(userId); // createToken 메서드 재사용
    }
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes()) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 검증 및 파싱
                .getBody()
                .getSubject(); // JWT의 subject에서 userId 또는 사용자 이름 반환
    }
    public List<String> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes()) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 검증 및 파싱
                .getBody();

        // "roles"라는 키에 저장된 권한 정보 가져오기
        return claims.get("roles", List.class);
    }


    // 인증 정보 추출
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 권한이 없으므로 빈 리스트를 반환
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(new String[0])
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key) // 서명 검증
                    .build()
                    .parseClaimsJws(token); // 토큰 구조 검증(jwt형식, 파싱 가능 여부)
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("유효하지 않은 JWT 토큰: {}", e.getMessage());
            return false;
        }
    }
}
