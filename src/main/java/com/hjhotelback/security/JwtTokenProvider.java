package com.hjhotelback.security;

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
    private long tokenValidityInSeconds; // 만료시간

    @Value("${jwt.secret}")
    private String secretKeyString; // 비밀 키

    private Key secretKey;

    private final MemberMapper memberMapper;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // JWT 생성
    public String generateToken(MemberEntity memberEntity) {
        List<MemberAuthEntity> memberAuths = memberMapper.findMemberAuth(memberEntity.getMemberId());
        List<String> authorities = memberAuths.stream()
                .map(MemberAuthEntity::getAuth) // MemberAuthEntity에서 auth를 추출
                .collect(Collectors.toList());
        long now = System.currentTimeMillis();
        Date validity = new Date(now + tokenValidityInSeconds * 1000);

        return Jwts.builder()
                // Header
                .signWith(secretKey, SignatureAlgorithm.HS512)
                // PayLoad
                // -- 등록 클레임
                .setSubject(memberEntity.getUserId())
                .setIssuedAt(new Date(now)) // 토큰 생성 시간
                .setExpiration(validity) // 만료 시간 설정
                // -- 사용자 클레임
                .claim("userName", memberEntity.getName())
                .claim("auths", authorities)
                .compact();
    }

    // JWT에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // 서명 키 설정
                .build() // JwtParser 생성
                .parseClaimsJws(token) // JWT 파싱
                .getBody();

        // 사용자 ID (subject) 반환
        return claims.getSubject();
    }

    // JWT에서 사용자 권한을 추출하는 예시 메서드 (optional)
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("auths", List.class);
    }

    // JWT에서 Authentication 객체를 생성
    public Authentication getAuthentication(String token) {

        // 사용자 이름(ID) 가져오기
        String username = getUserIdFromToken(token);

        // 권한 정보 가져오기
        List<String> roles = getRolesFromToken(token);

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체 생성
        UserDetails userDetails = new User(username, "", authorities);

        // UsernamePasswordAuthenticationToken 생성하여 반환
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
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

}
