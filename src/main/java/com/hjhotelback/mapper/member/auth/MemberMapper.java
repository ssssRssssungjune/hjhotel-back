package com.hjhotelback.mapper.member.auth;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface MemberMapper {

    @Insert("""
    INSERT INTO member (user_id, email, password, name, phone, status, is_active, created_at)
    VALUES (#{userId}, #{email}, #{password}, #{name}, #{phone}, 'ACTIVE', 1, CURRENT_TIMESTAMP)
""")
    void insertMember(String userId, String email, String password, String name, String phone);

    @Select("SELECT password FROM member WHERE user_id = #{userId}")
    String findPasswordByUserId(String userId);

    @Select("SELECT user_id AS userId, name FROM member WHERE user_id = #{userId}")
    Map<String, String> findMemberByUserId(@Param("userId") String userId);

}
