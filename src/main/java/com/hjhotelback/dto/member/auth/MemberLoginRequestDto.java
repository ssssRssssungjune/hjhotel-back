package com.hjhotelback.dto.member.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginRequestDto {

    @NotBlank // 문자열에 대해 널이 아니고 비어 있지 않아야 함
    @Size(min = 3, max = 30, message = "이름은 3자에서 30자 사이여야 합니다.")
    private String userId;    // 사용자 ID

    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자에서 20자 사이여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#]).{8,20}$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수 문자를 각각 최소 하나씩 포함해야 합니다.")
    private String password;  // 비밀번호

}
