package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.entity.StaffEntity;
import com.hjhotelback.security.JwtTokenProvider;
import com.hjhotelback.service.member.auth.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = staffService.login(request.getStaffUserId(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

