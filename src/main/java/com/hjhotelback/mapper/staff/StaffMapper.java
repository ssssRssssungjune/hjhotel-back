package com.hjhotelback.mapper.staff;

import com.hjhotelback.entity.staff.StaffEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StaffMapper {
    StaffEntity findByStaffUserId(@Param("staffUserId") String staffUserId); // XML 참조


    // 스태프 전체 목록 조회
    List<StaffEntity> findAllStaff();

    // 스태프 상태 변경
    void updateStaffStatus(@Param("staffId") int staffId, @Param("isActive") boolean isActive);

    // 스태프 삭제
    void deleteStaff(@Param("staffId") int staffId);
}
