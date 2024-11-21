package com.hjhotelback.service.member;

import com.hjhotelback.dto.member.MemberLoginRequest;
import com.hjhotelback.mapper.member.MemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public boolean authenticateMember(MemberLoginRequest request) {

        String storedPassword = memberMapper.findPasswordByEmail(request.getEmail());
        if (storedPassword == null) {
            return false;  //이메일 실휴패
        }

        // 비번 비교
        return request.getPassword().equals(storedPassword);
    }
}
