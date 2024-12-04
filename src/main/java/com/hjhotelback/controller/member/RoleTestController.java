package com.hjhotelback.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@EnableMethodSecurity  // 메서드 수준 보안 활성화
public class RoleTestController {

    // 누구나 접근할 수 있는 엔드포인트
    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("모든 사용자가 접근 가능합니다.");
    }

    // ROLE_USER 권한을 가진 사용자만 접근 가능
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> userEndpoint() {
        return ResponseEntity.ok("USER 권한을 가진 사용자만 접근 가능합니다.");
    }

    // ROLE_ADMIN 권한을 가진 사용자만 접근 가능
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("ADMIN 권한을 가진 사용자만 접근 가능합니다.");
    }
}