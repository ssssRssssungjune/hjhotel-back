package com.hjhotelback.service.member.auth;


import com.hjhotelback.dto.member.auth.MemberLoginRequestDto;
import com.hjhotelback.entity.MemberEntity;
import com.hjhotelback.mapper.member.auth.MemberMapper;
import com.hjhotelback.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MemberService(PasswordEncoder passwordEncoder, MemberMapper memberMapper, JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.memberMapper = memberMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public String login(MemberLoginRequestDto loginRequest) {
        // 사용자 ID로 데이터베이스에서 사용자 조회
        Optional<MemberEntity> member = memberMapper.findMemberByUserId(loginRequest.getUserId());
        MemberEntity memberEntity = member.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String storedPassword = memberEntity.getPassword();

        // 비밀번호 검증
        if (storedPassword == null || !passwordEncoder.matches(loginRequest.getPassword(), storedPassword)) {
            throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // JWT 생성 및 반환
        return jwtTokenProvider.generateToken(memberEntity,"USER");
    }


    public MemberEntity getMemberByUserId(String userId) {
        Optional<MemberEntity> result = memberMapper.findMemberByUserId(userId);
        return result.orElseThrow();
    }

    // 모든 멤버 가져오기
    public List<Map<String, Object>> getAllMembers() {
        return memberMapper.findAllMembers();
    }
}



