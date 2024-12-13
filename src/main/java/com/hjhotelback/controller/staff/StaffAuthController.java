package com.hjhotelback.controller.staff;

import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.entity.staff.StaffEntity;
import com.hjhotelback.security.JwtTokenProvider;
import com.hjhotelback.service.staff.StaffService;
import com.hjhotelback.utils.JwtCookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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





            return ResponseEntity.ok("로그인 완료"); // 또는 ResponseEntity.ok("Login successful");
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


    @GetMapping("/me")
    public ResponseEntity<?> getAdminDetails(HttpServletRequest request) {
        // SecurityContextHolder에서 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자가 아니거나, ADMIN 권한이 없으면 401 Unauthorized 반환
        if (authentication == null || !authentication.isAuthenticated() || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        // 사용자 ID 가져오기
        String staffUserId = authentication.getName(); // JwtTokenProvider에서 subject에 사용자 ID를 저장한다고 가정

        // 사용자 ID로 StaffEntity 조회
        StaffEntity staff = null;
        try {
            staff = staffService.findByStaffUserId(staffUserId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        // 사용자 정보를 맵에 담아 반환
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("staffUserId", staff.getStaffUserId());
        responseBody.put("role", staff.getRoleName());
        responseBody.put("name", staff.getName());
        responseBody.put("email", staff.getEmail());

        return ResponseEntity.ok(responseBody);
    }

}