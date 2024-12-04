
package com.hjhotelback.mapper.staff;

import com.hjhotelback.entity.staff.StaffEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StaffMapper {

    @Select("SELECT s.*, sr.role_name " +
            "FROM staff s " +
            "JOIN staff_role sr ON s.role_id = sr.role_id " +
            "WHERE s.staff_user_id = #{staffUserId}")
    StaffEntity findByStaffUserId(String staffUserId);


}
