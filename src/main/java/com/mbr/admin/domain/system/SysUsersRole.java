package com.mbr.admin.domain.system;



import com.mbr.admin.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="sys_users_roles")
public class SysUsersRole extends BaseEntity {
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "user_id")
    private Long userId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
