package com.hjhotelback.security;

import com.hjhotelback.entity.staff.StaffEntity;
import com.hjhotelback.entity.MemberAuthEntity;
import com.hjhotelback.entity.MemberEntity;
import com.hjhotelback.mapper.member.auth.MemberMapper;
import com.hjhotelback.service.staff.StaffService;
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
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    public String generateAdminToken(StaffEntity staffEntity, String roleName) {
        if (!"ADMIN".equals(roleName)) {
            throw new IllegalArgumentException("Only ADMIN role can generate admin tokens");
        }

        return Jwts.builder()
                .setSubject(staffEntity.getStaffUserId())
                .claim("role", roleName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 3600 * 1000)) // 2시간 만료
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // JWT 생성 메서드
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

    // 일반 사용자 JWT 생성
    public String generateToken(MemberEntity memberEntity, String role) {
        List<String> authorities = memberMapper.findMemberAuth(memberEntity.getMemberId())
                .stream()
                .map(MemberAuthEntity::getAuth)
                .collect(Collectors.toList());

        return createToken(memberEntity.getUserId(), role, authorities, tokenValidityInSeconds * 1000);
    }



    // JWT에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // JWT에서 권한 추출
    public List<String> getRolesFromToken(String token) {
        return parseClaims(token).get("auths", List.class);
    }

    // JWT에서 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        String username = getUserIdFromToken(token);
        List<SimpleGrantedAuthority> authorities = getRolesFromToken(token)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails userDetails = new User(username, "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // JWT 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT에서 Claims 추출
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
