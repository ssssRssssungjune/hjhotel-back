package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.JwtResponseDto;
import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.dto.member.auth.SignupRequest;
import com.hjhotelback.service.member.auth.AuthService;
import com.hjhotelback.service.member.auth.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;

    // 두 서비스 의존성을 하나의 생성자로 통합
    public AuthController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    // 회원가입 엔드포인트
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        authService.registerUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody MemberLoginRequestDto loginRequest) {
        JwtResponseDto response = memberService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
