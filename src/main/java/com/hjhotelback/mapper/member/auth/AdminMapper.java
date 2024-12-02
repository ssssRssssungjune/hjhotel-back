package com.hjhotelback.mapper.member.auth;

import com.hjhotelback.entity.AdminEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE admin_user_id = #{adminUserId}")
    AdminEntity findByAdminUserId(@Param("adminUserId") String adminUserId);
}