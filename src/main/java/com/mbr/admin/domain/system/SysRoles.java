package com.mbr.admin.domain.system;



import com.mbr.admin.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="sys_roles")
public class SysRoles extends BaseEntity {
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_desc")
    private String roleDesc;
    @Column(name = "enable")
    private Boolean enable;
    @Column(name = "role_auth")
    private String roleAuth;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getRoleAuth() {
        return roleAuth;
    }

    public void setRoleAuth(String roleAuth) {
        this.roleAuth = roleAuth;
    }
}
