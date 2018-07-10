package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.MerchantResource;
import com.mbr.admin.domain.merchant.MerchantVsResource;
import com.mbr.admin.domain.merchant.Vo.MerchantVsResourceVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MerchantVsResourceDao extends TkMapper<MerchantVsResource> {

    @Insert("<script>" +
            "insert into merchant_vs_resource values"+
            "<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">" +
                "(null,#{item.merchantId},#{item.resourceId},#{item.status},null,null,null,null,#{item.channel})"+
            "</foreach>"+
            "</script>")
    public int insertList(@Param("list") List<MerchantVsResource> list);

    @Select("select * from merchant_vs_resource where merchant_id=#{merchantId}")
    public List<MerchantVsResource> queryMerchantVsResource(@Param("merchantId")String merchantId);

    @Update("update merchant_vs_resource set channel=#{channel} where merchant_id=#{merchantId}")
    public int updateChannel(@Param("merchantId")String merchantId,@Param("channel")String channel);


    @Select("<script>" +
            "select mvr.id,mr.status,mvr.merchant_id as merchantId,mvr.resource_id as resourceId,mvr.create_time as createTime,mvr.update_time as updateTime," +
            "mvr.create_user_name as createUserName,mvr.update_user_name as updateUserName,mvr.channel,mr.url from merchant_resource as mr,merchant_vs_resource as mvr " +
            "where mr.id=mvr.resource_id"+
            "<if test=\"merchantId!=null and merchantId!=''\">" +
            "and mvr.merchant_id = #{merchantId}"+
            "</if>"+
            "</script>")
    public List<MerchantVsResourceVo> queryList(@Param("merchantId")String merchantId);


    @Delete("delete from merchant_vs_resource where id=#{id}")
    public int deleteMerchantVsResource(@Param("id")String id);

    @Select("select * from merchant_resource")
    public List<MerchantResource> queryAllMerchantResource();

    @Insert("insert into merchant_vs_resource values(#{mvr.id},#{mvr.merchantId},#{mvr.resourceId},#{mvr.status},#{mvr.createTime},null,#{mvr.createUserName},null,#{mvr.channel})")
    public int insertMerchantVsResource(@Param("mvr")MerchantVsResource mvr);

    @Select("select * from merchant_vs_resource where merchant_id=#{merchantId} and resource_id = #{resourceId}")
    public Object queryMerchantVsResourceByCondition(@Param("merchantId")String merchantId,@Param("resourceId")Long resourceId);
 }
