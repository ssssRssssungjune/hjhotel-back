package com.hjhotelback.dto.member.auth;

import lombok.Data;

@Data

public class MemberLoginRequest {

    private String userId;
    private String password;
}