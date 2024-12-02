package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.AdminJwtResponseDto;
import com.hjhotelback.dto.member.auth.AdminLoginRequestDto;
import com.hjhotelback.entity.AdminEntity;
import com.hjhotelback.security.JwtTokenProvider;
import com.hjhotelback.service.member.auth.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminService adminService;  // Admin 인증 서비스
    private final JwtTokenProvider jwtTokenProvider; // JWT 발급 유틸리티

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody AdminLoginRequestDto requestDto) {
        // Admin 인증
        AdminEntity admin = adminService.authenticateAdmin(requestDto.getStaffUserId(), requestDto.getPassword());

        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // JWT 발급 (어드민용)
        String token = jwtTokenProvider.generateAdminToken(admin, "ADMIN");  // AdminEntity 사용

        // JWT 응답 반환
        return ResponseEntity.ok(new AdminJwtResponseDto(token));
    }
}
