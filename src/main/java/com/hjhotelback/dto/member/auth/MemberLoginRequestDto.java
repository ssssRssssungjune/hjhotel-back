package com.hjhotelback.dto.member.auth;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String userId;    // 사용자 ID
    private String password;  // 비밀번호
}
