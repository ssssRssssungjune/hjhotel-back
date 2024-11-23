package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.dto.member.auth.MemberRegisterRequestDto;
import com.hjhotelback.dto.member.auth.JwtResponseDto;
import com.hjhotelback.service.member.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // 인증 관련 엔드포인트
public class AuthController {

    private final AuthService authService;

    // 생성자 주입
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 회원가입 요청
     * @param request 회원가입 요청 데이터 (MemberRegisterRequestDto)
     * @return JWT 토큰을 포함한 응답 (JwtResponseDto)
     */
    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> register(@RequestBody MemberRegisterRequestDto request) {
        String token = authService.registerUser(request); // 회원가입 후 JWT 발급
        System.out.println("Generated Token: " + token); // 디버그용 출력
        return ResponseEntity.ok(new JwtResponseDto(token)); // JWT 응답 반환
    }

    /**
     * 로그인 요청
     * @param request 로그인 요청 데이터 (MemberLoginRequestDto)
     * @return JWT 토큰을 포함한 응답 (JwtResponseDto)
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody MemberLoginRequestDto request) {
        String token = authService.loginUser(request); // 로그인 후 JWT 발급
        return ResponseEntity.ok(new JwtResponseDto(token)); // JWT 응답 반환
    }
}
