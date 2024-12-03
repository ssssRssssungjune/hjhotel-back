package com.hjhotelback.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffEntity {
    private Long staffId;        // 직원 ID
    private String staffUserId;  // 직원 사용자 ID
    private String password;     // 암호화된 비밀번호
    private Integer roleId;      // 역할 ID (staff_role 테이블 참조)
    private String name;         // 직원 이름
    private Boolean isActive;    // 활성화 여부
    private String position;     // 직책
}