package com.hjhotelback.mapper.member;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    // 이메일로 비밀번호 조회
    @Select("SELECT password FROM member WHERE email = #{email}")
    public String findPasswordByEmail(String email);
}
