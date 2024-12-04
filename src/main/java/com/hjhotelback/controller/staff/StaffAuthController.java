
package com.hjhotelback.controller.staff;

import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.service.staff.StaffService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class StaffAuthController {

    private final StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody StaffLoginRequestDto loginRequest, HttpServletResponse response) {
        StaffJwtResponseDto jwtResponse = staffService.loginWithStaffId(loginRequest);

        // HTTP-Only 쿠키 설정
        Cookie jwtCookie = new Cookie("JWT", jwtResponse.getToken());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // HTTPS에서만 전송 (로컬 환경에서는 false로 설정 가능)
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(2 * 60 * 60); // 2시간

        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // 쿠키 삭제
        Cookie jwtCookie = new Cookie("JWT", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); //HTTPS 환경에서 true
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // 쿠키 즉시 만료

        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }
}
