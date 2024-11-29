package com.hjhotelback.controller.staff;

import com.hjhotelback.service.staff.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // 로그인 처리
        String token = staffService.login(email, password);

        // 토큰 반환
        return ResponseEntity.ok(Map.of("token", token));
    }
}
