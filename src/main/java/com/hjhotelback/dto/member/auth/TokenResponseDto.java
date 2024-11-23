package com.hjhotelback.dto.member.auth;

import lombok.Data;

@Data
public class TokenResponseDto {
    private String token; // JWT 토큰

    public TokenResponseDto(String token) {
        this.token = token;
    }
}
