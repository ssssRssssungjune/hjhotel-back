package com.hjhotelback.mapper.staff;

//import com.hjhotelback.entity.staff.StaffRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StaffRoleMapper {
//    StaffRoleEntity findRoleById(@Param("roleId") Integer roleId); // XML 참조
//    String findRoleNameById(@Param("roleId") int roleId); // XML 참조
    void insertRole(@Param("roleName") String roleName, @Param("permissions") String permissions); // XML 참조
}
