package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.entity.StaffEntity;
import com.hjhotelback.security.JwtTokenProvider;
import com.hjhotelback.service.member.auth.StaffServiceuuuuu;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class StaffAuthController {

    private final StaffServiceuuuuu staffServiceuuuuu;  // Admin 인증 서비스
    private final JwtTokenProvider jwtTokenProvider; // JWT 발급 유틸리티

    @PostMapping("/login")
    public ResponseEntity<?> staffLogin(@RequestBody StaffLoginRequestDto requestDto) {
        // Admin 인증
        StaffEntity admin = staffServiceuuuuu.authenticateStaff(requestDto.getStaffUserId(), requestDto.getPassword());

        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // JWT 발급 (어드민용)
        String token = jwtTokenProvider.generateAdminToken(admin, "ADMIN");  // AdminEntity 사용

        // JWT 응답 반환
        return ResponseEntity.ok(new StaffJwtResponseDto(token));
    }
}
