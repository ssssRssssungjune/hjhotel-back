package com.hjhotelback.controller.staff;

import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.service.staff.StaffService;
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

    /**
     * 관리자 로그인
     * @param loginRequest 로그인 요청 DTO
     * @param response HTTP 응답 객체
     * @return ResponseEntity<Void>
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody StaffLoginRequestDto loginRequest, HttpServletResponse response) {
        // 로그인 처리 후 JWT 생성
        StaffJwtResponseDto jwtResponse = staffService.loginWithStaffId(loginRequest);

        // HTTP-Only 쿠키 설정
        ResponseCookie jwtCookie = createJwtToken(jwtResponse.getToken());

        // 응답 헤더에 쿠키 추가
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok().build();
    }

    /**
     * 관리자 로그아웃
     * @param response HTTP 응답 객체
     * @return ResponseEntity<Void>
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // JWT 쿠키 삭제
        ResponseCookie cookie = deleteJwtToken();

        // 응답 헤더에 쿠키 추가 (삭제)
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }

    /**
     * JWT 쿠키 생성 메서드
     * @param jwt JWT 토큰
     * @return ResponseCookie
     */
    private ResponseCookie createJwtToken(String jwt) {
        return ResponseCookie.from("JWT", jwt)
                .httpOnly(true)  // HttpOnly 옵션 설정
                .secure(false)  // HTTPS에서만 전송 (필요시 true)
                .path("/")  // 전체 경로에서 사용 가능
                .maxAge(2 * 60 * 60)  // 만료 시간: 2시간
                .sameSite("Strict")  // CSRF 방지
                .build();
    }

    /**
     * JWT 쿠키 삭제 메서드
     * @return ResponseCookie
     */
    private ResponseCookie deleteJwtToken() {
        return ResponseCookie.from("JWT", "")
                .httpOnly(true)  // HttpOnly 옵션 설정
                .secure(false)  // HTTPS에서만 사용 (필요시 true)
                .path("/")  // 전체 경로에서 삭제 가능
                .maxAge(0)  // 만료 시간 0으로 설정
                .sameSite("Strict")  // CSRF 방지
                .build();
    }
}
