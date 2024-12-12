package com.hjhotelback.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        // 로그인 경로는 필터링 제외
        if ("/api/admin/login".equals(requestUri) || "/api/users/login".equals(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT 추출
        String jwtToken = extractJwtFromRequest(request);

        // JWT 검증
        if (jwtToken == null || !jwtTokenProvider.validateToken(jwtToken)) {
            log.error("JWT 검증 실패: 토큰이 없거나 유효하지 않음");
            filterChain.doFilter(request, response);
            return;
        }

        // SecurityContext에 Authentication 저장
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    // JWT에서 쿠키 또는 헤더 추출
    private String extractJwtFromRequest(HttpServletRequest request) {
        // Authorization 헤더 확인
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 접두사 제거 후 반환
        }

        // 쿠키 확인 (fallback)
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT".equals(cookie.getName())) {
                    log.debug("JWT 쿠키 발견: {}", cookie.getValue());
                    return cookie.getValue();
                }
            }
        }

        log.warn("JWT 토큰을 찾을 수 없습니다.");
        return null;
    }
}