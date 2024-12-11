package com.hjhotelback.mapper.staff;

import com.hjhotelback.entity.staff.StaffEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StaffMapper {
    StaffEntity findByStaffUserId(@Param("staffUserId") String staffUserId); // XML 참조
}
