package com.hjhotelback.service.member;

import com.hjhotelback.dto.member.MemberRegisterRequest;
import com.hjhotelback.mapper.member.MemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public void registerMember(MemberRegisterRequest request) {
        // 비밀번호 암호화 생략
        memberMapper.insertMember(request);
    }
}
