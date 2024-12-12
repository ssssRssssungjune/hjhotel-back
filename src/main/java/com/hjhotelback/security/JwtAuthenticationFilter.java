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

        // JWT에서 Role 추출
        String role = jwtTokenProvider.getRoleFromToken(jwtToken);
        
        log.info(role);

        // 요청 경로와 Role에 따른 접근 권한 확인
        if (isUnauthorizedAccess(role, requestUri)) {
            sendForbiddenResponse(response, "접근 권한이 없습니다.");
            return;
        }

        // SecurityContext에 Authentication 저장
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        log.info(
                "Authentication 객체 정보: \n" +
                "  Principal: {}\n" +
                "  Authenticated: {}\n" +
                "  Authorities: {}",
                authentication.getPrincipal(),
                authentication.isAuthenticated(),
                authentication.toString()
               );
        filterChain.doFilter(request, response);
    }

    // JWT에서 쿠키 추출
    private String extractJwtFromRequest(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT".equals(cookie.getName())) {
                    log.debug("JWT 쿠키 발견: {}", cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        log.warn("JWT 쿠키가 요청에 포함되지 않았습니다.");
        return null;
    }

    // Role에 따른 접근 권한 확인
    private boolean isUnauthorizedAccess(String role, String requestUri) {
        if ("ADMIN".equals(role) && requestUri.startsWith("/api/member")) {
            return true;
        }
        if ("USER".equals(role) && requestUri.startsWith("/api/admin")) {
            return true;
        }
        return false;
    }

    // 권한 없는 요청에 Forbidden 응답
    private void sendForbiddenResponse(HttpServletResponse response, String message) throws IOException {
        log.error("403 Forbidden 응답: {}", message);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}
