package com.hjhotelback.security;

import com.hjhotelback.entity.MemberAuthEntity;
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
import java.util.stream.Collectors;

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
                staffEntity.getStaffUserId(), // subject
                roleName, // 역할
                List.of(roleName), // 권한처리를 위해 필요 (지은 주석 추가)
                2 * 3600 * 1000 // 만료 시간: 2시간
        );
    }

    // 일반 사용자용 JWT 생성
    public String generateToken(MemberEntity memberEntity, String role) {
        List<String> authorities = memberMapper.findMemberAuth(memberEntity.getMemberId())
                .stream()
                .map(MemberAuthEntity::getAuth)
                .collect(Collectors.toList());

//        return createToken(
//                memberEntity.getUserId(), // subject
//                role, // 역할
//                authorities, // 권한 리스트
//                tokenValidityInSeconds * 10000 // 만료 시간
        String token = createToken(
                memberEntity.getUserId(), // subject
                role, // 역할
                authorities, // 권한 리스트
                tokenValidityInSeconds * 10000 // 만료 시간
        );
          // 토큰 정보 로그 출력
        log.info("User: {}, Token: {}, UserId: {}, Role: {}, Authorities: {}", 
                "User", token, memberEntity.getUserId(), role, authorities);
        log.info("Authorities: {}", authorities);
        return token;
       
    }

    // JWT 생성 공통 메서드
    private String createToken(String subject, String role, List<String> authorities, long expirationTime) {
        long now = System.currentTimeMillis();
        Date validity = new Date(now + expirationTime);

        return Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .claim("role", role)
                .claim("auths", authorities)
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
    public Authentication getAuthentication(String jwtToken) {
        String username = getUserIdFromToken(jwtToken);
        log.info("토큰에서 추출한 username : {}", username); //(지은 추가)
        List<SimpleGrantedAuthority> authorities = getRolesFromToken(jwtToken)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        
        log.info("Extracted username: {}", username);
        log.info("Extracted authorities: {}", authorities);

//        UserDetails userDetails = new User(username, "", authorities);
//        return new UsernamePasswordAuthenticationToken(userDetails, jwtToken, authorities);
        UserDetails userDetails = new User(username, "", authorities);
        log.info("추출한 권한 : {}", authorities.toString());
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, jwtToken, authorities);
        log.info("Generated Authentication: {}", authentication);

        return authentication;
    }
}
