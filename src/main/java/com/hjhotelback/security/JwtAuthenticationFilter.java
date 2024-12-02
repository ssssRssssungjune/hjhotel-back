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
            // 회원가입 및 로그인 엔드포인트로의 요청을 검사
            if (isSignupOrLoginRequest(request.getRequestURI()) && jwtTokenProvider.validateToken(jwtToken)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String jsonMessage = "{\"message\": \"이미 로그인된 사용자입니다.\"}";
                response.getWriter().write(jsonMessage);
                response.getWriter().flush();
                return; // 이미 토큰이 있는 사용자는 로그인 또는 회원가입을 할 수 없음
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

    // 로그아웃 요청인지 확인하는 메서드
    private boolean isLogoutRequest(String requestUri) {
        return requestUri.contains("/logout");
    }
}
