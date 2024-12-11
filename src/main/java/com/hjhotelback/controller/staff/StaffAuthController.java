package com.hjhotelback.controller.staff;

import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
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

    @GetMapping("/me")
    public ResponseEntity<?> getAdminDetails(@CookieValue(name = "JWT", required = false) String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        try {
            String staffUserId = jwtTokenProvider.getUserIdFromToken(jwt);
            var staff = staffService.findByStaffUserId(staffUserId);

            if (!"ADMIN".equals(staff.getRoleName())) {
                return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("Forbidden: Not an admin");
            }

            // roleName을 role 필드로 매핑하여 반환하는 예시
            Map<String, Object> response = new HashMap<>();
            response.put("staffUserId", staff.getStaffUserId());
            response.put("role", staff.getRoleName());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Invalid token");
        }
    }
}