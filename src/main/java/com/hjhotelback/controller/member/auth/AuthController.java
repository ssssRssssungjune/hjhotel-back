package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.JwtResponseDto;
import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.dto.member.auth.SignupRequestDto;
import com.hjhotelback.entity.MemberEntity;
import com.hjhotelback.mapper.member.auth.MemberMapper;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMapper memberMapper;

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


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequestDto signupRequestDto) {
        authService.registerUser(signupRequestDto);

        // JSON 형식으로 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "회원가입이 완료되었습니다.");


        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 상태 코드
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        // SecurityContextHolder에서 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // JWT에서 유저 ID 추출
        String userId = authentication.getName();

        // DB에서 유저 정보 조회
        Optional<MemberEntity> optionalUser = memberMapper.findMemberByUserId(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        MemberEntity user = optionalUser.get();

        // 유저 정보 반환
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("userId", user.getUserId());
        responseBody.put("name", user.getName());
//        responseBody.put("phone", user.getPhone());

        return ResponseEntity.ok(responseBody);
    }
}