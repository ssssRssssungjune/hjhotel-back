package com.hjhotelback.mapper.member;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    // 회원가입: user_id 포함
    @Insert("INSERT INTO member (user_id, email, password, name, phone) " +
            "VALUES (#{userId}, #{email}, #{password}, #{name}, #{phone})")
    void insertMember(String userId, String email, String password, String name, String phone);

    // user_id로 비밀번호 조회 (로그인에 사용)
    @Select("SELECT password FROM member WHERE user_id = #{userId}")
    String findPasswordByUserId(String userId);

    @Select("SELECT COUNT(*) > 0 FROM member WHERE user_id = #{userId}")
    boolean existsByUserId(String userId);
}
