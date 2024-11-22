package com.hjhotelback.service.member;

import com.hjhotelback.dto.member.auth.MemberLoginRequest;
import com.hjhotelback.dto.member.auth.MemberRegisterRequest;
import com.hjhotelback.mapper.member.MemberMapper;
import com.hjhotelback.security.JwtTokenProvider; // JWT 유틸리티
import org.springframework.security.crypto.password.PasswordEncoder; // PasswordEncoder 추가
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final JwtTokenProvider jwtTokenProvider; // JWT 유틸리티 추가
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    public MemberService(MemberMapper memberMapper, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    public String registerMember(MemberRegisterRequest request) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // user_id 포함하여 회원가입 처리
        memberMapper.insertMember(
                request.getUserId(),
                request.getEmail(),
                encodedPassword, // 암호화된 비밀번호 저장
                request.getName(),
                request.getPhone()
        );

        // 회원가입 후 JWT 발급
        return jwtTokenProvider.generateToken(request.getUserId()); // user_id로 JWT 생성
    }

    // 로그인 인증
    public String authenticateMember(MemberLoginRequest request) {
        // user_id를 기반으로 비밀번호 조회
        String storedPassword = memberMapper.findPasswordByUserId(request.getUserId());
        if (storedPassword == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자 ID입니다.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), storedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 인증 성공 시 JWT 발급
        return jwtTokenProvider.generateToken(request.getUserId()); // user_id로 JWT 생성
    }
}
