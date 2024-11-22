package com.hjhotelback.dto.member.auth;

import lombok.Data;

@Data
public class MemberRegisterRequest {
    private String userId;
    private String email;
    private String password;
    private String name;
    private String phone;
}
