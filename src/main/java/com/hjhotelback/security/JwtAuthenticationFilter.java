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
        if ("/api/admin/login".equals(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = extractJwtFromRequest(request);

        if (jwtToken == null || !jwtTokenProvider.validateToken(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

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
        log.warn("JWT 쿠키가 요청에 포함되지 않았습니다.");
        return null;
    }

    private boolean isUnauthorizedAccess(String role, String requestUri) {
        return ("ADMIN".equals(role) && requestUri.startsWith("/api/member")) ||
                ("USER".equals(role) && requestUri.startsWith("/api/admin"));
    }

    private void sendForbiddenResponse(HttpServletResponse response, String message) throws IOException {
        log.error("403 Forbidden 응답: {}", message);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}

