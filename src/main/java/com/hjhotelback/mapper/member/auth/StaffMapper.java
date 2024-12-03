package com.hjhotelback.mapper.member.auth;

import com.hjhotelback.entity.StaffEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface StaffMapper {

    // 직원 정보 조회 (관리자 역할 확인)
    @Select("""
        SELECT s.staff_id AS staffId, s.staff_user_id AS staffUserId, s.password, s.name, sr.role_name AS roleName
        FROM staff s
        JOIN staff_role sr ON s.role_id = sr.role_id
        WHERE s.staff_user_id = #{staffUserId} AND sr.role_name = 'ADMIN'
    """)
    Map<String, Object> findAdminByStaffUserId(@Param("staffUserId") String staffUserId);
}
