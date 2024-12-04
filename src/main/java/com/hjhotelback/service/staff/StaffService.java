package com.hjhotelback.service.staff;

import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.entity.staff.StaffEntity;
import com.hjhotelback.mapper.staff.StaffMapper;
import com.hjhotelback.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 관리자 로그인을 처리하는 메서드
//     * @param loginRequest 로그인 요청 DTO
//     * @return StaffJwtResponseDto
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

//    /**
//     * 특정 스태프 정보를 ID로 가져오기
//     * @param staffUserId 스태프 사용자 ID
//     * @return StaffEntity
//     */
//    public StaffEntity getStaffByUserId(String staffUserId) {
//        Optional<StaffEntity> staff = Optional.ofNullable(staffMapper.findByStaffUserId(staffUserId));
//        return staff.orElseThrow(() -> new IllegalArgumentException("스태프를 찾을 수 없습니다."));
//    }

//    /**
//     * 모든 스태프 정보 가져오기
//     * @return List<StaffEntity>
//     */
//    public List<StaffEntity> getAllStaffs() {
//        return staffMapper.findAllStaffs();
//    }
//
//    /**
//     * 새로운 스태프 추가
//     * @param staffEntity 스태프 엔티티
//     */
//    public void addNewStaff(StaffEntity staffEntity) {
//        // 비밀번호 암호화
//        staffEntity.setPassword(passwordEncoder.encode(staffEntity.getPassword()));
//        staffMapper.insertStaff(staffEntity);
//    }
//
//    /**
//     * 스태프 정보 업데이트
//     * @param staffEntity 업데이트할 스태프 엔티티
//     */
//    public void updateStaff(StaffEntity staffEntity) {
//        staffMapper.updateStaff(staffEntity);
//    }
//
//    /**
//     * 스태프 삭제
//     * @param staffUserId 삭제할 스태프 ID
//     */
//    public void deleteStaff(String staffUserId) {
//        staffMapper.deleteByStaffUserId(staffUserId);
//    }
}
