package com.hjhotelback.controller.member.auth;

import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.dto.member.auth.SignupRequestDto;
import com.hjhotelback.entity.MemberEntity;
import com.hjhotelback.security.JwtTokenProvider;
import com.hjhotelback.service.member.auth.AuthService;
import com.hjhotelback.service.member.auth.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입 엔드포인트
    @PostMapping("/register")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto) { // TODO 유효성검사 추가
        authService.registerUser(signupRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다."); // 201 코드
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequestDto loginRequest,
                                        HttpServletResponse response) { // TODO 유효성검사 추가

        // 로그인 후 토큰 받기
        String jwt = memberService.login(loginRequest);

        // 쿠키 생성 및 설정
        ResponseCookie jwtCookie = createJwtToken(jwt);

        // 응답 헤더에 쿠키 추가
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok("로그인이 성공으로 완료되었습니다."); // 200 코드
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        ResponseCookie cookie = deleteJwtToken();

        // 응답 헤더에 쿠키 추가 (삭제)
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("로그아웃되었습니다.");
    }

    // 권한 변경 후 토큰 재발행을 위한 엔드포인트
    @GetMapping("/reissue-token")
    public ResponseEntity<String> reissueToken(Authentication authentication,
                                               HttpServletResponse response) {
        // userId 가져오기
        String currentUsername = null;

        if (authentication != null && authentication.isAuthenticated()) {
            // 인증 객체에서 사용자 이름 가져오기
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                currentUsername = ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                currentUsername = (String) principal;
            }
        }

        if (currentUsername == null) {
            return ResponseEntity.status(403).body("사용자 정보를 가져올 수 없습니다.");
        }

        // 사용자의 최신 권한 정보 가져오기
        MemberEntity memberEntity = memberService.getMemberByUserId(currentUsername);


        // 새로운 JWT 토큰 생성
        String newJwtToken = jwtTokenProvider.generateToken(memberEntity);

        // 기존 JWT 쿠키 삭제하기 위해 만료 시간 0으로 설정
        ResponseCookie expiredCookie = deleteJwtToken();

        // 새로운 JWT 쿠키 설정
        ResponseCookie newCookie = createJwtToken(newJwtToken);

        // 응답에 기존 쿠키 삭제와 새로운 쿠키 설정
        response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, newCookie.toString());

        return ResponseEntity.ok("토큰이 재발행되었습니다.");
    }

    public ResponseCookie createJwtToken(String jwt) {
        return ResponseCookie.from("JWT", jwt)
                .httpOnly(true)  // HttpOnly 옵션 적용
                //.secure(true)  // HTTPS에서만 전송되도록 설정
                .path("/")  // 전체 애플리케이션에서 사용 가능하도록 설정
                .maxAge(24 * 60 * 60)  // 쿠키의 만료 시간 설정 (1일)
                .sameSite("Strict")  // CSRF 방지 (Strict 또는 Lax 설정)
                .build();

    }

    public ResponseCookie deleteJwtToken() {
        return ResponseCookie.from("JWT", "")
                .httpOnly(true)  // HttpOnly 옵션 설정
                //.secure(true)  // HTTPS에서만 사용하도록 설정 (필요에 따라 설정)
                .path("/")  // 애플리케이션의 모든 경로에서 쿠키 삭제 가능하도록 설정
                .maxAge(0)  // 만료 시간 0으로 설정하여 즉시 삭제
                .sameSite("Strict")  // CSRF 방지 (Strict 또는 Lax 설정)
                .build();
    }

}
