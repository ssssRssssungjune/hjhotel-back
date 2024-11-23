package com.hjhotelback.service.member.auth;

import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.dto.member.auth.MemberRegisterRequestDto;
import com.hjhotelback.mapper.member.MemberMapper;
import com.hjhotelback.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberMapper memberMapper, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 회원가입 로직
    public String registerUser(MemberRegisterRequestDto request) {
        if (memberMapper.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        memberMapper.insertMember(request.getUserId(), request.getEmail(), encodedPassword, request.getName(), request.getPhone());
        return jwtTokenProvider.createToken(request.getUserId()); // JWT 생성
    }

    // 로그인 로직
    public String loginUser(MemberLoginRequestDto request) {
        String storedPassword = memberMapper.findPasswordByUserId(request.getUserId());
        if (storedPassword == null || !passwordEncoder.matches(request.getPassword(), storedPassword)) {
            throw new IllegalArgumentException("잘못된 사용자 ID 또는 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(request.getUserId()); // JWT 생성
    }
}
