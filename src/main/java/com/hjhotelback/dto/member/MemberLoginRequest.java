package com.hjhotelback.dto.member;

import lombok.Data;

@Data

public class MemberLoginRequest {

    private String email;
    private String password;
}