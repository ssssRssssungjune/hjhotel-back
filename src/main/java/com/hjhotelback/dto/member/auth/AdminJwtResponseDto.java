package com.hjhotelback.dto.member.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminJwtResponseDto {
    private String token; // 발급된 JWT 토큰
}