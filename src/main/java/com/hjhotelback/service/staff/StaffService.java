
package com.hjhotelback.service.staff;

import com.hjhotelback.dto.member.auth.StaffJwtResponseDto;
import com.hjhotelback.dto.member.auth.StaffLoginRequestDto;
import com.hjhotelback.entity.staff.StaffEntity;
import com.hjhotelback.mapper.staff.StaffMapper;
import com.hjhotelback.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public StaffJwtResponseDto loginWithStaffId(StaffLoginRequestDto loginRequest) {
        StaffEntity staff = staffMapper.findByStaffUserId(loginRequest.getStaffUserId());

        if (staff == null || !passwordEncoder.matches(loginRequest.getPassword(), staff.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        if (!"ADMIN".equals(staff.getRoleName())) {
            throw new IllegalStateException("Only ADMIN can log in");
        }

        // 관리자 토큰 생성
        String token = jwtTokenProvider.generateAdminToken(staff, "ADMIN");
        return new StaffJwtResponseDto(staff.getStaffUserId(), token, "ADMIN"); // role 추가
    }


}
