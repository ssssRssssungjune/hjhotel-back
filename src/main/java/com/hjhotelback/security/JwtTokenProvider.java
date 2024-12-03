package com.hjhotelback.security;

import com.hjhotelback.entity.StaffEntity;
import com.hjhotelback.entity.MemberAuthEntity;
import com.hjhotelback.entity.MemberEntity;
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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds; // 토큰의 만료 시간 (초 단위)

    @Value("${jwt.secret}")
    private String secretKeyString; // JWT 서명에 사용할 비밀 키

    private Key secretKey;

    private final MemberMapper memberMapper; // 사용자 정보 조회를 위한 Mapper

    @PostConstruct
    public void init() {
        // 비밀 키를 초기화
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // 일반 사용자 JWT 생성
    public String generateToken(MemberEntity memberEntity, String role) {
        // 사용자의 권한을 DB에서 조회
        List<MemberAuthEntity> memberAuths = memberMapper.findMemberAuth(memberEntity.getMemberId());
        List<String> authorities = memberAuths.stream()
                .map(MemberAuthEntity::getAuth) // 권한 정보를 추출
                .collect(Collectors.toList());

        long now = System.currentTimeMillis();

        // 토큰 만료 시간 설정
        long expirationTime = tokenValidityInSeconds * 1000;
        Date validity = new Date(now + expirationTime);

        // JWT 생성
        return Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.HS512) // 서명 방식
                .setSubject(memberEntity.getUserId()) // 사용자 ID
                .setIssuedAt(new Date(now)) // 토큰 생성 시간
                .setExpiration(validity) // 토큰 만료 시간 설정
                .claim("userName", memberEntity.getName()) // 사용자 이름을 claim으로 저장
                .claim("role", role)  // 역할 (USER, ADMIN 등)을 claim으로 저장
                .claim("auths", authorities) // 사용자의 권한을 claim으로 저장
                .compact();
    }

    // 어드민 JWT 생성 (AdminEntity용)
    public String generateAdminToken(StaffEntity staffEntity, String role) {
        // 어드민의 권한을 설정
        List<String> authorities = List.of(role); // 예시로 ADMIN 역할만 포함

        long now = System.currentTimeMillis();

        // 토큰 만료 시간 설정 (어드민은 만료 시간을 다르게 설정)
        long expirationTime = tokenValidityInSeconds * 2 * 1000; // 어드민은 2배 긴 만료 시간
        Date validity = new Date(now + expirationTime);

        // JWT 생성
        return Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .setSubject(staffEntity.getStaffUserId()) // 어드민 사용자 ID
                .setIssuedAt(new Date(now)) // 토큰 생성 시간
                .setExpiration(validity) // 토큰 만료 시간 설정
                .claim("userName", staffEntity.getName()) // 어드민 이름
                .claim("role", role)  // 역할 (ADMIN)을 claim으로 저장
                .claim("auths", authorities) // 권한
                .compact();
    }

    // JWT에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // JWT 파싱
                .getBody();

        // 사용자 ID (subject)를 반환
        return claims.getSubject();
    }

    // JWT에서 사용자 권한을 추출하는 예시 메서드 (optional)
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 사용자 권한을 리스트로 반환
        return claims.get("auths", List.class);
    }

    // JWT에서 Authentication 객체를 생성
    public Authentication getAuthentication(String token) {
        // 사용자 ID 가져오기
        String username = getUserIdFromToken(token);

        // 권한 정보 가져오기
        List<String> roles = getRolesFromToken(token);

        // SimpleGrantedAuthority 객체 생성
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체 생성
        UserDetails userDetails = new User(username, "", authorities);

        // UsernamePasswordAuthenticationToken 생성하여 반환
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // JWT 유효성 검사 (boolean 반환)
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token); // JWT 파싱
            return true; // 유효한 토큰
        } catch (Exception e) {
            return false; // 유효하지 않은 토큰
        }
    }

    // JWT에서 Claims 추출
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // Claims 반환
        } catch (Exception e) {
            return null; // 유효하지 않거나 파싱 실패 시 null 반환
        }
    }
}