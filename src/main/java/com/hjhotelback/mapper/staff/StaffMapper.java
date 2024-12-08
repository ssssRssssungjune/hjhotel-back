
package com.hjhotelback.mapper.staff;

import com.hjhotelback.entity.staff.StaffEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StaffMapper {

    // Staff 정보를 가져올 때 role_name을 포함
    @Select("""
    SELECT s.*, sr.role_name AS roleName
    FROM staff s
    JOIN staff_role sr ON s.role_id = sr.role_id
    WHERE s.staff_user_id = #{staffUserId}
      AND sr.role_name IN ('ADMIN', 'FRONT_DESK', 'HOUSEKEEPING')
""")
    StaffEntity findByStaffUserId(@Param("staffUserId") String staffUserId);

}

