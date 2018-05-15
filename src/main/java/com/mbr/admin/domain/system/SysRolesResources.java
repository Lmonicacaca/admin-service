package com.mbr.admin.domain.system;


import com.mbr.admin.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="sys_roles_resources")
public class SysRolesResources extends BaseEntity {

    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "resource_id")
    private Long resourceId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
