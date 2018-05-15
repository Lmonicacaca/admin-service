package com.mbr.admin.dao.system;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.system.SysUsers;
import com.mbr.admin.domain.system.vo.SysUsersVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysUsersDao extends TkMapper<SysUsers> {

    @Select("SELECT" +
            "            su.id AS id," +
            "            su.username AS username," +
            "            su.`password` AS `password`," +
            "            su.enabled AS enabled," +
            "            su.account_non_expired AS accountNonExpired," +
            "            su.account_non_locked AS accountNonLocked," +
            "            su.credentials_non_expired AS credentialsNonExpired," +
            "            sr.role_auth as roles" +
            "        FROM" +
            "            sys_users su," +
            "            sys_users_roles sur," +
            "            sys_roles sr" +
            "        WHERE" +
            "            su.id = sur.user_id" +
            "        AND su.username = #{username}" +
            "        AND sur.role_id = sr.id" +
            "        AND su.`status` = 0" +
            "        AND sur.`status` = 0" +
            "        AND sr.`status` = 0")
    public SysUsersVo loadByUsername(@Param(value = "username") String username);


    @Select("<script>" +
            "select s1.id,s1.username,s1.name,s1.last_login as lastLogin ,s1.login_ip as loginIp,s1.out_login_time as outLoginTime,s3.role_name as roleName, s3.id as roleId from sys_users s1 left join sys_users_roles s2 on s1.id=s2.user_id left" +
            "        join sys_roles s3 on s2.role_id = s3.id " +
            " <where>"+
            "        <if test=\"id != null and id != ''\">" +
            "            and s1.id = #{id}" +
            "        </if>" +
            "        <if test=\"username!=null and username !=''\">" +
            "            and s1.username like CONCAT('%',#{username},'%')" +
            "        </if>" +
            "        <if test=\"name!=null and name !=''\">" +
            "            and s1.name like CONCAT('%',#{name},'%')" +
            "        </if>" +
            "        and s1.enabled=1 and s1.status =0 and s2.status =0 and s3.status =0" +
            " </where>"+
            "        order by s1.create_time" +
            "</script>")
    public List<SysUsers> queryListByPage(SysUsers sysUsers);
}
