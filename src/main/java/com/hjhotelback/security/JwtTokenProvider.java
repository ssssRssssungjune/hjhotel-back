package com.hjhotelback.security;

import com.hjhotelback.entity.MemberAuthEntity;
import com.hjhotelback.entity.MemberEntity;
import com.hjhotelback.mapper.member.auth.MemberMapper;
import com.hjhotelback.service.member.auth.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds; // 만료시간

    @Value("${jwt.secret}")
    private String secretKeyString; // 비밀 키

    private Key secretKey;

    private final MemberMapper memberMapper;

    public JwtTokenProvider(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }


    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // JWT 생성
    public String generateToken(MemberEntity memberEntity) {
        List<MemberAuthEntity> memberAuths = memberMapper.findMemberAuth(memberEntity.getMemberId());
        String authorities = memberAuths.stream()
                .map(MemberAuthEntity::getAuth) // MemberAuthEntity에서 auth를 추출
                .collect(Collectors.joining(","));
        long now = System.currentTimeMillis();
        Date validity = new Date(now + tokenValidityInSeconds * 1000);

        return Jwts.builder()
                // Header
                .signWith(secretKey, SignatureAlgorithm.HS512)
                // PayLoad
                // -- 등록 클레임
                .setSubject(memberEntity.getName())
                .setIssuedAt(new Date(now)) // 토큰 생성 시간
                .setExpiration(validity) // 만료 시간 설정
                // -- 사용자 클레임
                .claim("userName", memberEntity.getName())
                .claim("auth", authorities)
                .compact();
    }

    // JWT에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // 서명 키 설정
                .build() // JwtParser 생성
                .parseClaimsJws(token) // JWT 파싱
                .getBody();
        return claims.getSubject(); // 사용자 ID 반환
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey) // 서명 키 설정
                    .build() // JwtParser 생성
                    .parseClaimsJws(token); // JWT 파싱
            return true; // 유효한 토큰
        } catch (Exception e) {
            return false; // 유효하지 않은 토큰
        }
    }

    // 토큰 유효 시간을 밀리초로 반환
    private long getTokenValidityInMilliseconds() {
        return tokenValidityInSeconds * 1000;
    }
}
