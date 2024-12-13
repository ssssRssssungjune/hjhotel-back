package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.JwtResponseDto;
import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.dto.member.auth.SignupRequestDto;
import com.hjhotelback.entity.MemberEntity;
import com.hjhotelback.security.JwtTokenProvider;
import com.hjhotelback.service.member.auth.AuthService;
import com.hjhotelback.service.member.auth.MemberService;
import com.hjhotelback.utils.JwtCookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto loginRequest, HttpServletResponse response) {
        try {
            // 사용자 로그인 처리 및 JWT 생성
            String jwt = memberService.login(loginRequest);


            // JWT 쿠키 생성
            ResponseCookie jwtCookie = JwtCookieUtils.createJwtToken(jwt);
            response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());



            return ResponseEntity.ok("로그인 완료"); // 또는 ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Authentication failed");
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // JWT 쿠키 삭제
        ResponseCookie cookie = JwtCookieUtils.deleteJwtToken();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("로그아웃되었습니다.");
    }
}
