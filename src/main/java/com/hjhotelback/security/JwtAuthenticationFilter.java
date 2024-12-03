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
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 로그아웃 요청은 JWT 필터에서 검증하지 않도록 예외 처리
        if (isLogoutRequest(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = extractJwtFromRequest(request);

        if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {
            // JWT가 유효한 경우 역할(Role)을 확인
            String role = jwtTokenProvider.getClaims(jwtToken).get("role", String.class);

            if (isUnauthorizedAccess(role, request.getRequestURI())) {
                // 역할과 엔드포인트가 맞지 않는 경우 접근 금지
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String jsonMessage = "{\"message\": \"접근 권한이 없습니다.\"}";
                response.getWriter().write(jsonMessage);
                response.getWriter().flush();
                return;
            }

            // JWT가 유효하다면 사용자 인증 정보를 SecurityContext에 설정
            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 필터 체인의 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        // 쿠키 확인
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                // 토큰 확인
                if (cookie.getName().equals("JWT")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private boolean isSignupOrLoginRequest(String requestUri) {
        return requestUri.contains("/signup") || requestUri.contains("/login");
    }

    private boolean isLogoutRequest(String requestUri) {
        return requestUri.contains("/logout");
    }

    private boolean isUnauthorizedAccess(String role, String requestUri) {
        if ("ADMIN".equals(role) && requestUri.startsWith("/api/member")) {
            // 스태프(ADMIN)는 멤버 전용 엔드포인트에 접근 불가
            return true;
        }
        if ("USER".equals(role) && requestUri.startsWith("/api/admin")) {
            // 멤버(USER)는 스태프 전용 엔드포인트에 접근 불가
            return true;
        }
        return false;
    }
}
