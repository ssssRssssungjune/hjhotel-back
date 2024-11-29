package com.hjhotelback.mapper.staff;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface StaffMapper {

    // 관리자 로그인 정보 조회
    @Select("""
        SELECT s.staff_id AS staffId, s.email, s.password, s.name, s.role_id AS roleId, sr.role_name AS roleName
        FROM staff s
        JOIN staff_role sr ON s.role_id = sr.role_id
        WHERE s.email = #{email} AND sr.role_name = 'ADMIN'
    """)
    Map<String, Object> findAdminByEmail(@Param("email") String email);
}
