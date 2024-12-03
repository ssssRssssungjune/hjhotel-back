package com.hjhotelback.mapper.staff;

import com.hjhotelback.entity.StaffEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//import java.util.Map;

@Mapper
public interface StaffMapper {
    @Select("""
        SELECT s.staff_id AS staffId,
               s.staff_user_id AS staffUserId,
               s.password,
               s.is_active AS isActive,
               sr.role_name AS roleName
        FROM staff s
        JOIN staff_role sr ON s.role_id = sr.role_id
        WHERE s.staff_user_id = #{staffUserId}
    """)
    StaffEntity findStaffByUserId(@Param("staffUserId") String staffUserId);
}
