package com.hjhotelback.mapper.member;

import com.hjhotelback.dto.member.MemberRegisterRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO member (email, password, name, phone, created_at) " +
            "VALUES (#{email}, #{password}, #{name}, #{phone}, NOW())")
    void insertMember(MemberRegisterRequest request);
}
