package com.hjhotelback.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffRoleEntity {
    private Integer roleId;
    private String roleName;
    private String permissions; // JSON 형식으로 저장된 권한 정보
    private String createdAt;
}
