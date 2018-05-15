package com.mbr.admin.dao.system;


import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.system.SysRoles;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysRolesDao extends TkMapper<SysRoles> {

    @Select("<script>" +
            "select s.id,s.role_name as roleName,s.role_auth as roleAuth from sys_roles s " +
            "<where>"+
            "        <if test=\"roleName!=null and roleName !=''\">" +
            "            and role_name like CONCAT('%',#{roleName},'%')" +
            "        </if>" +
            "        and s.status = 0"+
            " </where>"+
            "</script>")
    public List<SysRoles> select2(SysRoles sysRoles);


    @Select("<script>" +
            "select s.id,s.role_name as roleName,s.role_auth as roleAuth,s.role_desc as roleDesc from sys_roles s " +
            "<where>"+
            "        <if test=\"roleName!=null and roleName !=''\">" +
            "            and role_name like CONCAT('%',#{roleName},'%')" +
            "        </if>" +
            "        and s.status = 0" +
            " </where>"+
            "        order by s.create_time desc" +
            "</script>")
    public List<SysRoles> queryByPage(SysRoles sysRoles);
}
