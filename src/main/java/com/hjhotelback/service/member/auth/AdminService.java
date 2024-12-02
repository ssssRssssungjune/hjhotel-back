package com.hjhotelback.service.member.auth;

import com.hjhotelback.entity.AdminEntity;
import com.hjhotelback.mapper.member.auth.AdminMapper;
import com.hjhotelback.mapper.member.auth.AdminRoleMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;  // AdminMapper (admin 테이블 조회용)
    private final AdminRoleMapper adminRoleMapper;  // 수정된 부분: AdminRoleMapper (admin_role 테이블 조회용)
    private final PasswordEncoder passwordEncoder;

    public AdminEntity authenticateAdmin(String adminUserId, String password) {
        // admin_user_id로 어드민 조회
        AdminEntity admin = adminMapper.findByAdminUserId(adminUserId);

        if (admin == null || !passwordEncoder.matches(password, admin.getPassword())) {
            return null; // 인증 실패
        }

        // 역할 ID를 사용하여 role_name을 가져오기
        String roleName = adminRoleMapper.findRoleNameById(admin.getRoleId());
        admin.setRoleName(roleName); // AdminEntity에 roleName을 세팅

        return admin; // 인증 성공
    }
}