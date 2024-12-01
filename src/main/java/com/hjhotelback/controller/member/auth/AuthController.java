package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.dto.member.auth.SignupRequestDto;
import com.hjhotelback.service.member.auth.AuthService;
import com.hjhotelback.service.member.auth.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;

    @Autowired
    // 두 서비스 의존성을 하나의 생성자로 통합
    public AuthController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    // 회원가입 엔드포인트
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto) { // TODO 유효성검사 추가
        authService.registerUser(signupRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다."); // 201 코드
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequestDto loginRequest,
                                        HttpServletResponse response) { // TODO 유효성검사 추가

        // 로그인 후 토큰 받기
        String jwt = memberService.login(loginRequest);

        // 쿠키 생성 및 설정
        ResponseCookie jwtCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(true)  // HttpOnly 옵션 적용
                //.secure(true)  // HTTPS에서만 전송되도록 설정
                .path("/")  // 전체 애플리케이션에서 사용 가능하도록 설정
                .maxAge(24 * 60 * 60)  // 쿠키의 만료 시간 설정 (1일)
                .sameSite("Strict")  // CSRF 방지 (Strict 또는 Lax 설정)
                .build();

        // 응답 헤더에 쿠키 추가
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok("로그인이 성공으로 완료되었습니다."); // 200 코드
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // JWT 쿠키를 삭제하기 위해 maxAge를 0으로 설정
        ResponseCookie cookie = ResponseCookie.from("JWT", "")
                .httpOnly(true)  // HttpOnly 옵션 설정
                //.secure(true)  // HTTPS에서만 사용하도록 설정 (필요에 따라 설정)
                .path("/")  // 애플리케이션의 모든 경로에서 쿠키 삭제 가능하도록 설정
                .maxAge(0)  // 만료 시간 0으로 설정하여 즉시 삭제
                .sameSite("Strict")  // CSRF 방지 (Strict 또는 Lax 설정)
                .build();

        // 응답 헤더에 쿠키 추가 (삭제)
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("로그아웃되었습니다.");
    }
}
