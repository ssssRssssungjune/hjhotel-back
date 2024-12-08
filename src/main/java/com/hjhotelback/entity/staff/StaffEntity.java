
package com.hjhotelback.entity.staff;

import lombok.Data;

@Data
public class StaffEntity {
    private int staffId;
    private String staffUserId;
    private String password;
//    private StaffRoleEntity role;
    private String roleName; // 조인된 role_name
    private String email;
    private String name;
    private String phone;
    private String position;
    private boolean isActive;
    private String lastLoginAt;
    private String createdAt;
    private Integer roleId; // role_id
}
