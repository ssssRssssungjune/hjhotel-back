package com.hjhotelback.dto.member.auth;

import lombok.Data;

@Data

public class MemberLoginRequestDto {

    private String userId;
    private String password;
}