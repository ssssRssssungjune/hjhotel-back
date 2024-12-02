package com.hjhotelback.controller.member;

import com.hjhotelback.service.member.auth.AuthService;
import com.hjhotelback.service.member.auth.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

//    @PostMapping("/register")
//    public ResponseEntity<Map<String, String>> registerMember(@RequestBody SignupRequestDto request) {
////        memberService.registerMember(request);
//        authService.registerUser(request);
//
//
//        // JSON 형식으로 응답
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "회원가입이 완료되었습니다.");
//        response.put("status", "success"); // 성공 여부
//        response.put("redirectUrl", "/signupcom"); // 클라이언트가 이동해야 할 경로
//        response.put("userId", request.getUserId());
//
//        return ResponseEntity.ok(response);
//    }
//    @GetMapping("/info/{userId}")
//    public ResponseEntity<Map<String, String>> getUserInfo(@PathVariable String userId) {
//        Map<String, String> userInfo = memberService.getUserInfo(userId);
//
//        // Null 체크 및 빈 값 반환 처리
//        if (userInfo == null || userInfo.isEmpty()) {
//            return ResponseEntity.status(404).body(Map.of("message", "사용자를 찾을 수 없습니다."));
//        }
//
//        return ResponseEntity.ok(userInfo);
//    }

}

