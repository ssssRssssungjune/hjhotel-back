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

            // Response 객체 반환
            StaffJwtResponseDto response = new StaffJwtResponseDto(token);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 잘못된 자격 증명 처리
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 일반 예외 처리
        }
    }
}
