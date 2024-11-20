package com.hjhotelback.controller.member;

import com.hjhotelback.dto.member.MemberRegisterRequest;
import com.hjhotelback.service.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@RequestBody MemberRegisterRequest request) {
        memberService.registerMember(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

}
