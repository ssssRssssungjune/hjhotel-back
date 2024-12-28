package com.hjhotelback.controller.staff;

import com.hjhotelback.dto.member.auth.MemberDTO;
import com.hjhotelback.dto.member.auth.PageResponseDTO;
import com.hjhotelback.entity.staff.StaffEntity;
import com.hjhotelback.service.member.auth.MemberService;
import com.hjhotelback.service.staff.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class StaffController {

    private final MemberService memberService;
    private final StaffService staffService;

    public StaffController(MemberService memberService, StaffService staffService) {
        this.memberService = memberService;
        this.staffService = staffService;
    }

    // ---------------------- 회원(Member) 관리 ----------------------

    // 회원 전체 목록 조회
    @GetMapping("/member")
    public ResponseEntity<PageResponseDTO<MemberDTO>> getMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponseDTO<MemberDTO> result = memberService.getMembers(page, size);
        return ResponseEntity.ok(result);
    }

    // 회원 상태 변경
    @PutMapping("/member/{id}/status")
    public String updateMemberStatus(@PathVariable("id") int memberId, @RequestParam("newStatus") String newStatus) {
        memberService.updateMemberStatus(memberId, newStatus);
        return "회원 상태가 성공적으로 변경되었습니다.";
    }

    // 회원 삭제
    @DeleteMapping("/member/{id}")
    public String deleteMember(@PathVariable("id") int memberId) {
        memberService.deleteMember(memberId);
        return "회원이 성공적으로 삭제되었습니다.";
    }

    // ---------------------- 직원(Staff) 관리 ----------------------

    // 스태프 전체 목록 조회
    @GetMapping("/staff")
    public List<StaffEntity> getAllStaff() {
        return staffService.getAllStaff();
    }

    // 스태프 상세 조회
    @GetMapping("/staff/{staffUserId}")
    public StaffEntity getStaffByUserId(@PathVariable String staffUserId) {
        return staffService.findByStaffUserId(staffUserId);
    }

    // 스태프 상태 변경
    @PutMapping("/staff/{staffId}/status")
    public String updateStaffStatus(@PathVariable int staffId, @RequestParam("isActive") boolean isActive) {
        staffService.updateStaffStatus(staffId, isActive);
        return "스태프 상태가 성공적으로 변경되었습니다.";
    }

    // 스태프 삭제
    @DeleteMapping("/staff/{staffId}")
    public String deleteStaff(@PathVariable int staffId) {
        staffService.deleteStaff(staffId);
        return "스태프가 성공적으로 삭제되었습니다.";
    }
}
