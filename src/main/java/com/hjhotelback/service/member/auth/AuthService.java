package com.hjhotelback.service.member.auth;

import com.hjhotelback.dto.member.auth.MemberLoginRequest;
import com.hjhotelback.dto.member.auth.MemberRegisterRequest;
import com.hjhotelback.mapper.member.MemberMapper;
import com.hjhotelback.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    public AuthService(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, MemberMapper memberMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.memberMapper = memberMapper;
    }

    // 회원가입 로직
    public String registerUser(MemberRegisterRequest request) {
        // 중복 체크: userId가 이미 존재하는지 확인
        if (memberMapper.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 회원 정보 저장
        memberMapper.insertMember(
                request.getUserId(),
                request.getEmail(),
                encodedPassword,
                request.getName(),
                request.getPhone()
        );

        // JWT 생성 후 반환
        return jwtTokenProvider.generateToken(request.getUserId());
    }

    // 로그인 로직
    public String loginUser(MemberLoginRequest request) {
        // 사용자 ID로 회원 정보 조회
        String storedPassword = memberMapper.findPasswordByUserId(request.getUserId());

        if (storedPassword == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자 ID입니다.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), storedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 후 반환
        return jwtTokenProvider.generateToken(request.getUserId());
    }
}
