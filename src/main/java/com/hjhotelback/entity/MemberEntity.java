package com.hjhotelback.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberEntity {
    private int memberId;
    private String userId;
    private String email;
    private String password;
    private String name;
    private String phone;
    private MemberStatus status;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
