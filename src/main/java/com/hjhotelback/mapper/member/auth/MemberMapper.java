package com.hjhotelback.mapper.member.auth;

import com.hjhotelback.entity.MemberAuthEntity;
import com.hjhotelback.entity.MemberEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Insert("""
        INSERT INTO member (user_id, email, password, name, phone, status, is_active, created_at)
        VALUES (#{userId}, #{email}, #{password}, #{name}, #{phone}, 'ACTIVE', 1, CURRENT_TIMESTAMP)
    """)
    @Options(useGeneratedKeys = true, keyProperty = "memberId", keyColumn = "member_id")
    void insertMember(MemberEntity memberEntity);

    @Insert("INSERT INTO member_auth (member_id, auth) values (#{memberId}, #{auth})")
    void insertMemberAuth(MemberAuthEntity memberAuthEntity);

    @Select("SELECT * FROM member_auth WHERE member_id = #{memberId}")
    List<MemberAuthEntity> findMemberAuth(int memberId);

    //아이디랑 비밀번호 유효성 검사
    @Select("SELECT * FROM member WHERE member_id = #{memberId}")
    Optional<MemberEntity> findMemberByMemberId(@Param("memberId") int memberId);

    //회원가입 완료시 나오는페이지 정보 가져오는
    @Select("SELECT * FROM member WHERE user_id = #{userId}")
    Optional<MemberEntity> findMemberByUserId(@Param("userId") String userId);

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
