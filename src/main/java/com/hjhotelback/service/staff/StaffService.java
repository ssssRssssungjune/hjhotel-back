package com.hjhotelback.service.staff;

import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.entity.staff.StaffEntity;
import com.hjhotelback.mapper.staff.StaffMapper;
import com.hjhotelback.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 관리자 로그인을 처리하는 메서드
     */
    public StaffJwtResponseDto loginWithStaffId(StaffLoginRequestDto loginRequest) {
        // 사용자 조회
        StaffEntity staff = staffMapper.findByStaffUserId(loginRequest.getStaffUserId());
        log.info("Staff 정보: {}", staff); // 테스트 log 출력 (지은 추가)

        // 사용자 확인 및 비밀번호 검증
        if (staff == null || !passwordEncoder.matches(loginRequest.getPassword(), staff.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // 관리자 권한 확인
        if (!"ADMIN".equals(staff.getRoleName())) {
            throw new IllegalStateException("관리자 권한이 필요합니다.");
        }

        // JWT 생성
        String token = jwtTokenProvider.generateAdminToken(staff, staff.getRoleName());
        return new StaffJwtResponseDto(staff.getStaffUserId(), token, staff.getRoleName()); //token에 staffUserId, jwt token, 권한(ADMIN)
    }

    /**
     * staffUserId로 Staff 정보 조회 메서드 추가
     */
    public StaffEntity findByStaffUserId(String staffUserId) {
        StaffEntity staff = staffMapper.findByStaffUserId(staffUserId);
        if (staff == null) {
            throw new IllegalArgumentException("해당 staffUserId에 해당하는 Staff를 찾을 수 없습니다: " + staffUserId);
        }
        return staff;
    }
}



