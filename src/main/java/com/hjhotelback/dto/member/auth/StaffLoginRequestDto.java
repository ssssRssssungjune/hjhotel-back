// package: com.hjhotelback.dto.member.auth
package com.hjhotelback.dto.member.auth;

import lombok.Data;

@Data
public class StaffLoginRequestDto {
    private String staffUserId;
    private String password;
}
