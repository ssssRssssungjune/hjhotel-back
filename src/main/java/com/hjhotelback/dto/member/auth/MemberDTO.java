package com.hjhotelback.dto.member.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private int memberId;         // 회원 고유 ID
    private String userId;        // 사용자 ID
    private String email;         // 이메일
    private String name;          // 이름
    private String phone;         // 연락처
    private String status;        // 회원 상태
    private boolean isActive;     // 계정 활성 여부
    private String role;          // 권한 (USER, ADMIN)
    private String createdAt;     // 계정 생성 일시
    private String updatedAt;     // 계정 수정 일시
}
