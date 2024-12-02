package com.hjhotelback.mapper.member.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminRoleMapper {

    @Select("SELECT role_name FROM admin_role WHERE role_id = #{roleId}")
    String findRoleNameById(int roleId);
}