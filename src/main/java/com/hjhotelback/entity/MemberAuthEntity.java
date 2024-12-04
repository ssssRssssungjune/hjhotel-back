package com.hjhotelback.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberAuthEntity {
    private int authNo;
    private int memberId;
    private String auth;
    private boolean is_deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
