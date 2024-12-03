package com.hjhotelback.service.member.auth;

import com.hjhotelback.entity.MemberEntity;
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
    private final PasswordEncoder passwordEncoder; // 비밀번호 검증용
    private final JwtTokenProvider jwtTokenProvider; // JWT 생성

    public StaffService(StaffMapper staffMapper, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.staffMapper = staffMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(String staffUserId, String password) {
        // 직원 정보 조회
        StaffEntity staff = staffMapper.findStaffByUserId(staffUserId);

        // 유효성 검증
        if (staff == null || !staff.getIsActive() || !passwordEncoder.matches(password, staff.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        // ADMIN 권한 확인
        if (!"ADMIN".equals(staff.getRoleName())) {
            throw new RuntimeException("관리자 권한이 없습니다.");
        }

        // JWT 생성 후 반환
        return jwtTokenProvider.generateToken(staff);
    }
}
