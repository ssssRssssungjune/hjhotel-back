package com.hjhotelback.security;

import com.hjhotelback.entity.MemberEntity;
import com.hjhotelback.entity.staff.StaffEntity;
import com.hjhotelback.mapper.member.auth.MemberMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final MemberMapper memberMapper;

    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds; // 토큰 만료 시간 (초 단위)

    @Value("${jwt.secret}")
    private String secretKeyString; // JWT 서명에 사용할 비밀 키
    private Key secretKey;

    @PostConstruct
    public void init() {
        // 비밀 키 초기화
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // 관리자용 JWT 생성
    public String generateAdminToken(StaffEntity staffEntity, String roleName) {
        if (!"ADMIN".equals(roleName)) {
            throw new IllegalArgumentException("Only ADMIN role can generate admin tokens");
        }
        return createToken(
                staffEntity.getStaffUserId(), // subject (사용자 ID)
                roleName, // 역할
                List.of(roleName), // 권한
                2 * 3600 * 1000, // 만료 시간: 2시간
                staffEntity.getName(), // 사용자 이름
                staffEntity.getEmail() // 이메일 추가
        );
    }

    // 일반 사용자용 JWT 생성
    public String generateToken(MemberEntity memberEntity, String role) {
        return createToken(
                memberEntity.getUserId(), // Subject (사용자 ID)
                role, // 역할(Role)
                List.of(role), // 권한 리스트
                tokenValidityInSeconds * 10000, // 만료 시간
                memberEntity.getName(), // 사용자 이름
                memberEntity.getEmail() // 이메일 추가
        );
    }

    // JWT 생성 공통 메서드
    private String createToken(String subject, String role, List<String> authorities, long expirationTime, String userName, String email) {
        long now = System.currentTimeMillis();
        Date validity = new Date(now + expirationTime);

        return Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .setSubject(subject) // 사용자 ID를 Subject로 설정
                .setIssuedAt(new Date(now)) // 발급 시간
                .setExpiration(validity) // 만료 시간
                .claim("role", role) // 역할(Role)
                .claim("name", userName) // 사용자 이름
                .claim("email", email) // 이메일
                .claim("auths", authorities) // 권한 리스트
                .compact();
    }

    // JWT 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseClaims(token); // Claims를 파싱하여 유효성 검사
            return true;
        } catch (Exception e) {
            log.error("JWT 유효성 검사 실패: {}", e.getMessage());
            return false;
        }
    }

    // JWT에서 Claims 파싱
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // JWT에서 역할(Role) 추출
    public String getRoleFromToken(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // JWT에서 권한(Auth) 리스트 추출
    public List<String> getRolesFromToken(String token) {
        return parseClaims(token).get("auths", List.class);
    }

    // JWT에서 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        String username = getUserIdFromToken(token); // 사용자 ID 추출
        List<String> roles = getRolesFromToken(token); // 역할(Role) 목록 추출

        // Role 정보를 SimpleGrantedAuthority로 변환
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        // UserDetails 객체 생성
        UserDetails userDetails = new User(username, "", authorities);

        // Authentication 객체 반환
        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }
}