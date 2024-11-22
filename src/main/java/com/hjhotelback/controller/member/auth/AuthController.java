package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.MemberLoginRequest;
import com.hjhotelback.dto.member.auth.MemberRegisterRequest;
import com.hjhotelback.dto.member.auth.JwtResponse;
import com.hjhotelback.service.member.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody MemberRegisterRequest request) {
        String token = authService.registerUser(request);
        System.out.println("Generated Token: " + token); // 토큰 확인
        return ResponseEntity.ok(new JwtResponse(token));
    }



    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody MemberLoginRequest request) {
        String token = authService.loginUser(request); // 로그인 후 JWT 생성
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
