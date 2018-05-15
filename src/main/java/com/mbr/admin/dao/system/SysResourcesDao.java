package com.mbr.admin.dao.system;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.system.SysResources;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysResourcesDao extends TkMapper<SysResources> {

    @Select("<script>" +
            " select DISTINCT" +
            "        s4.id," +
            "        s4.resource_name as resourceName," +
            "        s4.resource_desc as resourceDesc," +
            "        s4.resource_path as resourcePath," +
            "        s4.resource_parent as resourceParent," +
            "        s4.enable, s4.resource_icon as resouceIcon," +
            "        s4.order_no as orderNo," +
            "        s4.resource_level as resouceLevel" +
            "        from sys_users_roles s1,sys_roles s2 ,sys_roles_resources s3,sys_resources s4" +
             "<where>"+
            "        s1.role_id=s2.id and s2.id=s3.role_id and s3.resource_id = s4.id" +
            "        and s4.`enable`=1 and s1.STATUS=0 and s2.status=0 and s3.STATUS=0 and s4.status=0" +
            "        <if test=\"userId!=null\">" +
            "           and s1.user_id=#{userId}" +
            "        </if>" +
            " </where>"+
            "        order by s4.order_no " +
            "</script>"
    )
    List<SysResources> queryAllByUserId(@Param(value = "userId") Long userId);

    /**
     * Query all resource list.
     *查询所有资源信息
     * @param sysResources the sys resources
     * @return the list
     */
    @Select("<script>" +
            "select" +
            "        id," +
            "        resource_name as resourceName," +
            "        resource_desc as resourceDesc," +
            "        resource_path as resourcePath," +
            "        resource_parent as resourceParent," +
            "        enable," +
            "        resource_icon as resouceIcon," +
            "        order_no as orderNo," +
            "        resource_level as resourceLevel," +
            "        create_time as createTime" +
            "        from sys_resources" +
            "<where>"+
            "        <if test=\"id != null and id !='' \">" +
            "            and id =#{id}" +
            "        </if>" +
            "        <if test=\"resourceName != null and resourceName !='' \">" +
            "            and resource_name like CONCAT('%',#{resourceName},'%')" +
            "        </if>" +
            "        and STATUS=0" +
            " </where>"+
            "        order by order_no ASC" +
            "</script>")
    public List<SysResources>  queryAllResource(SysResources sysResources);


}
