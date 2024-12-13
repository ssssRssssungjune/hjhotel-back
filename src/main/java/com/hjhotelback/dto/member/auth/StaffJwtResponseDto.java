package com.hjhotelback.dto.member.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StaffJwtResponseDto {
    private String staffUserId;
    private String token;
    private String roleName;
}
