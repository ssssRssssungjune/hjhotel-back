package com.hjhotelback.mapper.member.auth;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {

    @Insert("""
    INSERT INTO member (user_id, email, password, name, phone, status, is_active, created_at)
    VALUES (#{userId}, #{email}, #{password}, #{name}, #{phone}, 'ACTIVE', 1, CURRENT_TIMESTAMP)
""")
    void insertMember(String userId, String email, String password, String name, String phone);

    //아이디랑 비밀번호 유효성 검사
    @Select("SELECT password FROM member WHERE user_id = #{userId}")
    String findPasswordByUserId(String userId);

    //회원가입 완료시 나오는페이지 정보 가져오는
    @Select("SELECT user_id AS userId, name FROM member WHERE user_id = #{userId}")
    Map<String, String> findMemberByUserId(@Param("userId") String userId);

    //관리자페이지에서 회원조회
    @Select("""
        SELECT 
            member_id, 
            user_id, 
            email, 
            name, 
            phone, 
            status, 
            is_active, 
            created_at, 
            updated_at 
        FROM member
    """)
    List<Map<String, Object>> findAllMembers();

}
