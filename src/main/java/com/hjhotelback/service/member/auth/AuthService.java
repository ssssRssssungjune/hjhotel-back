package com.hjhotelback.service.member.auth;

import com.hjhotelback.dto.member.auth.SignupRequest;
import com.hjhotelback.mapper.member.auth.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberMapper memberMapper; // 회원 정보를 처리할 매퍼
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    public AuthService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignupRequest signupRequest) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        // 회원 정보 저장
        memberMapper.insertMember(
                signupRequest.getUserId(),
                signupRequest.getEmail(),
                encodedPassword,
                signupRequest.getName(),
                signupRequest.getPhone()
        );
    }
}
