package com.hjhotelback.entity.staff;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffRoleEntity {
    private Integer roleId;      // 역할 ID (기본 키)
    private String roleName;     // 역할 이름 (예: ADMIN, USER)
    private String permissions;  // 권한 정보 (JSON 또는 문자열)
    private String createdAt;    // 생성 시간
}