package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.service.member.auth.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class StaffAuthController {

    private final StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<StaffJwtResponseDto> login(@RequestBody StaffLoginRequestDto request) {
        try {
            // StaffService에서 토큰 생성 및 검증
            String token = staffService.login(request.getStaffUserId(), request.getPassword());

            // JWT 토큰 응답 반환
            StaffJwtResponseDto response = new StaffJwtResponseDto(token);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // 잘못된 자격 증명 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new StaffJwtResponseDto("아이디 또는 비밀번호가 잘못되었습니다."));
        } catch (SecurityException e) {
            // 권한 없음 처리
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new StaffJwtResponseDto("권한이 없습니다."));
        }
    }
}
