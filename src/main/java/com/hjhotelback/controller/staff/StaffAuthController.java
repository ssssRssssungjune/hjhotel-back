package com.hjhotelback.controller.staff;

import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.entity.staff.StaffEntity;
import com.hjhotelback.security.JwtTokenProvider;
import com.hjhotelback.service.staff.StaffService;
import com.hjhotelback.utils.JwtCookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class StaffAuthController {

    private final StaffService staffService;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody StaffLoginRequestDto loginRequest, HttpServletResponse response) {
        try {
            // 관리자 로그인 및 토큰 생성
            StaffJwtResponseDto jwtResponse = staffService.loginWithStaffId(loginRequest);
            String jwt = jwtResponse.getToken();

            // JWT 쿠키 생성
            ResponseCookie cookie = JwtCookieUtils.createJwtToken(jwt);
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            // 사용자 정보 가져오기 (StaffEntity 사용)
            StaffEntity staff = staffService.findByStaffUserId(jwtResponse.getStaffUserId());

            // 응답 DTO 생성 (StaffJwtResponseDto 사용)
            StaffJwtResponseDto responseBody = StaffJwtResponseDto.builder()
                    .token(jwt)
                    .staffUserId(staff.getStaffUserId())
                    .roleName(staff.getRoleName())
//                    .name(staff.getName())
//                    .email(staff.getEmail())
                    .build();

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // JWT 쿠키 삭제
        ResponseCookie cookie = JwtCookieUtils.deleteJwtToken();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }

    // /api/admin/me 엔드포인트 제거
}