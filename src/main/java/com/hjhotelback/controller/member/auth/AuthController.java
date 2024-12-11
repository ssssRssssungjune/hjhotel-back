package com.hjhotelback.controller.member.auth;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequestDto loginRequest, HttpServletResponse response) {
        String jwt = memberService.login(loginRequest);

        // JWT 쿠키 생성
        ResponseCookie jwtCookie = JwtCookieUtils.createJwtToken(jwt);
        response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok("로그인이 성공으로 완료되었습니다.");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // JWT 쿠키 삭제
        ResponseCookie cookie = JwtCookieUtils.deleteJwtToken();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("로그아웃되었습니다.");
    }
}
