package com.hjhotelback.service.staff;

import com.hjhotelback.mapper.staff.StaffRoleMapper;
import org.springframework.stereotype.Service;

@Service
public class StaffRoleService {

    private final StaffRoleMapper staffRoleMapper;

    public StaffRoleService(StaffRoleMapper staffRoleMapper) {
        this.staffRoleMapper = staffRoleMapper;
    }

    public void addRole(String roleName, String permissions) {
        staffRoleMapper.insertRole(roleName, permissions);
    }
}
