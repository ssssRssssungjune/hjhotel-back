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

    public void registerMember(MemberRegisterRequest request) {
        // MemberMapper 호출: 필드를 개별적으로 전달
        memberMapper.insertMember(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhone()
        );
    }

    public boolean authenticateMember(MemberLoginRequest request) {
        String storedPassword = memberMapper.findPasswordByEmail(request.getEmail());
        if (storedPassword == null) {
            return false;  // 이메일 존재하지 않음
        }

        // 비밀번호 비교
        return request.getPassword().equals(storedPassword);
    }
}
