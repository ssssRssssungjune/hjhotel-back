package com.hjhotelback.controller.member;

import com.hjhotelback.dto.member.MemberLoginRequest;
import com.hjhotelback.dto.member.MemberRegisterRequest;
import com.hjhotelback.service.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerMember(@RequestBody MemberRegisterRequest request) {
        memberService.registerMember(request);

        // JSON 형식으로 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "회원가입이 완료되었습니다.");
        response.put("status", "success"); // 성공 여부
        response.put("redirectUrl", "/signupcom"); // 클라이언트가 이동해야 할 경로

        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginMember(@RequestBody MemberLoginRequest request) {
        boolean isAuthenticated = memberService.authenticateMember(request);

        if (isAuthenticated) {
            return ResponseEntity.ok("로그인 성공!");
        } else {
            return ResponseEntity.status(401).body("로그인 실패: 잘못된 이메일 또는 비밀번호입니다.");
        }

    }
}