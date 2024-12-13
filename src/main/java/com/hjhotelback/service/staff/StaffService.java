package com.hjhotelback.service.staff;

import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.entity.staff.StaffEntity;
import com.hjhotelback.mapper.staff.StaffMapper;
import com.hjhotelback.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;



    /**
     * 관리자 로그인을 처리하는 메서드
     */
    public StaffJwtResponseDto loginWithStaffId(StaffLoginRequestDto staffLoginRequestDto) {
        // 사용자 조회
        StaffEntity staff = staffMapper.findByStaffUserId(staffLoginRequestDto.getStaffUserId());

        // 사용자 확인 및 비밀번호 검증
        if (staff == null || !passwordEncoder.matches(staffLoginRequestDto.getPassword(), staff.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // 관리자 권한 확인
        if (!"ADMIN".equals(staff.getRoleName())) {
            throw new IllegalStateException("관리자 권한이 필요합니다.");
        }

        // JWT 생성
        String token = jwtTokenProvider.generateAdminToken(staff, staff.getRoleName());
        return new StaffJwtResponseDto(staff.getStaffUserId(), token, staff.getRoleName());
    }

    // 스태프 전체 목록 조회
    public List<StaffEntity> getAllStaff() {
        return staffMapper.findAllStaff();
    }

    // 스태프 상세 조회
    public StaffEntity findByStaffUserId(String staffUserId) {
        StaffEntity staff = staffMapper.findByStaffUserId(staffUserId);
        if (staff == null) {
            throw new IllegalArgumentException("해당 스태프를 찾을 수 없습니다: " + staffUserId);
        }
        return staff;
    }

    // 스태프 상태 변경
    public void updateStaffStatus(int staffId, boolean isActive) {
        staffMapper.updateStaffStatus(staffId, isActive);
    }

    // 스태프 삭제
    public void deleteStaff(int staffId) {
        staffMapper.deleteStaff(staffId);
    }
}




