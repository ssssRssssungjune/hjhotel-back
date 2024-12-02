package com.hjhotelback.auth;

import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.dto.member.auth.SignupRequestDto;
import com.hjhotelback.service.member.auth.AuthService;
import com.hjhotelback.service.member.auth.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Log4j2
public class MemberServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    @Autowired
    private MemberService memberService;

    @Test
    public void registerMember() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
        String uno = LocalDateTime.now().format(formatter);
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .userId("user" + uno)
                .email("user" + uno + "@gmail.com")
                .password("1234")
                .name("user" + uno)
                .phone("0" + uno)
                .build();

        int memberId = authService.registerUser(signupRequestDto);

        log.info("member_id: {}", memberId);
    }

    @Test
    public void login() {
        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder()
                .userId("user1201223819")
                .password("1234")
                .build();

        String token = memberService.login(memberLoginRequestDto);

        log.info("JWT: {}", token);
    }

}
