package com.hjhotelback.service.member.auth;


import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.dto.member.auth.JwtResponseDto;
import com.hjhotelback.dto.member.auth.SignupRequest;
import com.hjhotelback.mapper.member.auth.MemberMapper;
import com.hjhotelback.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void registerMember(SignupRequest signupRequest) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        // 데이터베이스에 사용자 정보 저장
        memberMapper.insertMember(
                signupRequest.getUserId(),
                signupRequest.getEmail(),
                encodedPassword,
                signupRequest.getName(),
                signupRequest.getPhone()
        );
    }

    public JwtResponseDto login(MemberLoginRequestDto loginRequest) {
        // 사용자 ID로 데이터베이스에서 사용자 조회
        String storedPassword = memberMapper.findPasswordByUserId(loginRequest.getUserId());
        if (storedPassword == null || !passwordEncoder.matches(loginRequest.getPassword(), storedPassword)) {
            throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // JWT 생성 및 반환
        String token = jwtTokenProvider.generateToken(loginRequest.getUserId());
        return new JwtResponseDto(token, loginRequest.getUserId());
    }
}
