package com.hjhotelback.dto.member.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDto {
    private String token;     // JWT 토큰
    private String userId;    // 사용자 ID
}
