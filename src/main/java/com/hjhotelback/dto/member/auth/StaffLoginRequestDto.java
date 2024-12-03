package com.hjhotelback.dto.member.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffLoginRequestDto {
    private String staffUserId; // 관리자 ID
    private String password;    // 관리자 비밀번호
}