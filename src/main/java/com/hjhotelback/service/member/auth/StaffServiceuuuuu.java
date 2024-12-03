package com.hjhotelback.service.staff;

import com.hjhotelback.entity.StaffEntity;
import com.hjhotelback.mapper.staff.StaffMapper;
import com.hjhotelback.mapper.member.auth.StaffRoleMapper;
import com.hjhotelback.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class StaffService {

    private final StaffMapper staffMapper;
    private final StaffRoleMapper staffRoleMapper; // 역할 조회를 위해 사용
    private final PasswordEncoder passwordEncoder; // 비밀번호 비교용
    private final JwtTokenProvider jwtTokenProvider;

    public StaffService(StaffMapper staffMapper, StaffRoleMapper staffRoleMapper,
                        PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.staffMapper = staffMapper;
        this.staffRoleMapper = staffRoleMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 직원 인증 처리
     */
    public StaffEntity authenticateStaff(String staffId, String password) {
        StaffEntity staff = staffMapper.findByStaffUserId(staffId);
        if (staff == null || !passwordEncoder.matches(password, staff.getPassword())) {
            return null; // 인증 실패
        }
        String roleName = staffRoleMapper.findRoleNameById(staff.getRoleId());
        staff.setPosition(roleName); // 역할 이름 설정
        return staff; // 인증 성공
    }

    /**
     * 관리자 로그인 처리 (아이디 기반)
     */
    public String login(String staffId, String password) {
        // 관리자 정보 조회
        Map<String, Object> admin = staffMapper.findAdminByStaffId(staffId);

        if (admin == null || !passwordEncoder.matches(password, admin.get("password").toString())) {
            throw new RuntimeException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        // JWT 생성
        return jwtTokenProvider.generateToken(admin.get("staffId").toString());
    }
}
