package com.hjhotelback.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffEntity {
    private Long staffId;        // 직원 ID (staff 테이블의 staff_id)
    private String staffUserId;  // 직원 사용자 ID (staff 테이블의 staff_user_id)
    private String password;     // 암호화된 비밀번호 (staff 테이블의 password)
    private Integer roleId;      // 역할 ID (staff 테이블의 role_id)
    private String name;         // 직원 이름 (staff 테이블의 name)
    private Boolean isActive;    // 활성화 여부 (staff 테이블의 is_active)
    private String roleName;     // 역할 이름 (staff_role 테이블의 role_name) - 별도로 가져와야 함
    private String position;
}