package com.hjhotelback.service.staff;

import com.hjhotelback.mapper.staff.StaffMapper;
import com.hjhotelback.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StaffService {

    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public StaffService(StaffMapper staffMapper, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.staffMapper = staffMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    public String login(String email, String password) {
//        // 관리자 정보 조회
//        Map<String, Object> admin = staffMapper.findAdminByEmail(email);
//
//        if (admin == null || !passwordEncoder.matches(password, admin.get("password").toString())) {
//            throw new RuntimeException("이메일 또는 비밀번호가 잘못되었습니다.");
//        }
//
//        // JWT 생성
//        return jwtTokenProvider.generateToken(admin.get("staffId").toString());
//    }
}
