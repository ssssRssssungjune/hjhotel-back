package com.hjhotelback.mapper.member.auth;

import com.hjhotelback.entity.MemberAuthEntity;
import com.hjhotelback.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    void insertMember(MemberEntity memberEntity); // XML의 insertMember 참조

    void insertMemberAuth(MemberAuthEntity memberAuthEntity); // XML의 insertMemberAuth 참조

    List<MemberAuthEntity> findMemberAuth(@Param("memberId") int memberId); // XML의 findMemberAuth 참조

//    Optional<MemberEntity> findMemberByMemberId(@Param("memberId") int memberId); // XML의 findMemberByMemberId 참조

    Optional<MemberEntity> findMemberByUserId(@Param("userId") String userId); // XML의 findMemberByUserId 참조

    List<Map<String, Object>> findAllMembers(); // XML의 findAllMembers 참조
}
