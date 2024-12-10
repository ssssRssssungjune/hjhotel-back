package com.hjhotelback.controller.staff;

import com.hjhotelback.service.member.auth.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/member")
public class StaffController {

    private final MemberService memberService;

    public StaffController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 전체 목록 조회
    @GetMapping
    public List<Map<String, Object>> getAllMembers() {
        return memberService.getAllMembers();
    }

    // 회원 상태 변경
    @PutMapping("/{id}/status")
    public String updateMemberStatus(@PathVariable("id") int memberId, @RequestParam("newStatus") String newStatus) {
        memberService.updateMemberStatus(memberId, newStatus);
        return "회원 상태가 성공적으로 변경되었습니다.";
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable("id") int memberId) {
        memberService.deleteMember(memberId);
        return "회원이 성공적으로 삭제되었습니다.";
    }
}
