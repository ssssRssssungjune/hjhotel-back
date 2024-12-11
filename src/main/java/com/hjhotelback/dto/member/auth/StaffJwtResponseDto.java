package com.hjhotelback.dto.member.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffJwtResponseDto {
    private String staffUserId;
    private String token;
    private String role;
}
