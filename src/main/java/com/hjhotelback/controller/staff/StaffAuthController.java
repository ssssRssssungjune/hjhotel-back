package com.hjhotelback.controller.staff;

import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.service.staff.StaffService;
import com.hjhotelback.utils.JwtCookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class StaffAuthController {

    private final StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody StaffLoginRequestDto loginRequest, HttpServletResponse response) {
        try {
            StaffJwtResponseDto jwtResponse = staffService.loginWithStaffId(loginRequest);

            // JWT 쿠키 생성
            ResponseCookie cookie = JwtCookieUtils.createJwtToken(jwtResponse.getToken());
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(jwtResponse);
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
}
