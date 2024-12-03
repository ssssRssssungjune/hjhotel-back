package com.hjhotelback.dto.member.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StaffInfoResponseDto {
    private String staffUserId; // 관리자 ID
    private String name;        // 관리자 이름
    private String role;        // 관리자 역할
}


//나중에 관리자 정보 반환하는 DTO