package com.hjhotelback.mapper.member;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Insert("INSERT INTO member (email, password, name, phone) VALUES (#{email}, #{password}, #{name}, #{phone})")
    void insertMember(String email, String password, String name, String phone);

    @Select("SELECT password FROM member WHERE email = #{email}")
    String findPasswordByEmail(String email);
}

