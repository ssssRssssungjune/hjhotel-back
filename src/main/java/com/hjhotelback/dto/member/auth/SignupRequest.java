package com.hjhotelback.dto.member.auth;

import lombok.Data;

@Data
public class SignupRequest {
    private String userId; // 사용자 ID
    private String email;  // 이메일 주소
    private String password; // 비밀번호
    private String name; // 이름
    private String phone; // 연락처
}
