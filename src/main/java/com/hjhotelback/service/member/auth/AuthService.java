package com.hjhotelback.service.member.auth;

import com.hjhotelback.dto.member.auth.SignupRequestDto;
import com.hjhotelback.entity.MemberAuthEntity;
import com.hjhotelback.entity.MemberEntity;
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

    public int registerUser(SignupRequestDto signupRequestDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        MemberEntity memberEntity = MemberEntity.builder()
                .userId(signupRequestDto.getUserId())
                .email(signupRequestDto.getEmail())
                .password(encodedPassword)
                .name(signupRequestDto.getName())
                .phone(signupRequestDto.getPhone())
                .build();

        // 회원 정보 저장
        memberMapper.insertMember(memberEntity);

        MemberAuthEntity memberAuthEntity = MemberAuthEntity.builder()
                .memberId(memberEntity.getMemberId())
                .auth("ROLE_USER")
                .build();

        // 권한 저장
        memberMapper.insertMemberAuth(memberAuthEntity);

        // 등록 순번 반환
        return memberEntity.getMemberId();
    }
}
