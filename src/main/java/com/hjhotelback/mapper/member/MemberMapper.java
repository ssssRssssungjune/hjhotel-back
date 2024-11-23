package com.hjhotelback.mapper.member;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    // 회원가입
    @Insert("INSERT INTO member (user_id, email, password, name, phone) " +
            "VALUES (#{userId}, #{email}, #{password}, #{name}, #{phone})")
    void insertMember(String userId, String email, String password, String name, String phone);

    // user_id로 비밀번호 조회
    @Select("SELECT password FROM member WHERE user_id = #{userId}")
    String findPasswordByUserId(String userId);

    // user_id 중복 여부 확인
    @Select("SELECT COUNT(*) > 0 FROM member WHERE user_id = #{userId}")
    boolean existsByUserId(String userId);
}
