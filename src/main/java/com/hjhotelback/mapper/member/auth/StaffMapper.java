package com.hjhotelback.mapper.member.auth;

import com.hjhotelback.entity.StaffEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StaffMapper {

    @Select("SELECT * FROM staff WHERE staff_user_id = #{staffUserId}")
    StaffEntity findByStaffUserId(@Param("staffUserId") String staffUserId);
}