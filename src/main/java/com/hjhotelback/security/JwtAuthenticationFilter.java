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
        String jwtToken = extractJwtFromRequest(request);

        // 로그아웃 요청 또는 유효하지 않은 JWT는 필터링 제외
        if (isLogoutRequest(requestUri) || jwtToken == null || !jwtTokenProvider.validateToken(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 역할(Role) 확인 및 접근 제한
        String role = jwtTokenProvider.parseClaims(jwtToken).get("role", String.class);

        if (isUnauthorizedAccess(role, requestUri)) {
            sendForbiddenResponse(response, "접근 권한이 없습니다.");
            return;
        }

        // 인증 정보 설정
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private boolean isLogoutRequest(String requestUri) {
        return requestUri.contains("/logout");
    }

    private boolean isUnauthorizedAccess(String role, String requestUri) {
        return ("ADMIN".equals(role) && requestUri.startsWith("/api/member")) ||
                ("USER".equals(role) && requestUri.startsWith("/api/admin"));
    }

    private void sendForbiddenResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}
