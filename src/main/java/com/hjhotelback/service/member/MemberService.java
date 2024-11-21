package com.hjhotelback.service.member;

import com.hjhotelback.dto.member.MemberLoginRequest;
import com.hjhotelback.dto.member.MemberRegisterRequest;
import com.hjhotelback.mapper.member.MemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    // 회원가입
    public void registerMember(MemberRegisterRequest request) {
        // user_id 포함하여 회원가입 처리
        memberMapper.insertMember(
                request.getUserId(),
                request.getEmail(),
                request.getPassword(), // 암호화 없이 평문 저장
                request.getName(),
                request.getPhone()
        );
    }

    // 로그인 인증
    public boolean authenticateMember(MemberLoginRequest request) {
        // user_id를 기반으로 비밀번호 조회
        String storedPassword = memberMapper.findPasswordByUserId(request.getUserId());
        if (storedPassword == null) {
            return false; // user_id가 존재하지 않음
        }

        // 비밀번호 평문 비교
        return request.getPassword().equals(storedPassword);
    }
}
