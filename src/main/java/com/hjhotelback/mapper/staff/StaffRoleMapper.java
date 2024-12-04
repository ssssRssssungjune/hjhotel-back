package com.hjhotelback.mapper.staff;

import com.hjhotelback.entity.staff.StaffRoleEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StaffRoleMapper {

    // 역할 정보 조회 (roleId로 조회)
    @Select("""
        SELECT role_id AS roleId,
               role_name AS roleName,
               permissions,
               created_at AS createdAt
        FROM staff_role
        WHERE role_id = #{roleId}
    """)
    StaffRoleEntity findRoleById(@Param("roleId") Integer roleId);

    // 역할 이름 조회
    @Select("SELECT role_name FROM staff_role WHERE role_id = #{roleId}")
    String findRoleNameById(@Param("roleId") int roleId);

    // 역할 추가
    @Insert("""
        INSERT INTO staff_role (role_name, permissions, created_at)
        VALUES (#{roleName}, #{permissions}, NOW())
    """)
    void insertRole(@Param("roleName") String roleName, @Param("permissions") String permissions);
}
