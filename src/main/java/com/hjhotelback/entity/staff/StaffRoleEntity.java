package com.hjhotelback.entity.staff;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class StaffRoleEntity {
    private Integer roleId;      // 역할 ID (기본 키)
    private String roleName;     // 역할 이름 (예: ADMIN, USER)
    private String permissions;  // 권한 정보 (JSON 또는 문자열)
    private String createdAt;    // 생성 시간

//    // JSON을 Java List로 변환하는 메서드
//    public List<String> getPermissionsAsList() {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            return mapper.readValue(permissions, new TypeReference<List<String>>() {});
//        } catch (Exception e) {
//            throw new RuntimeException("JSON 파싱 오류: " + e.getMessage());
//        }
//    }
}

