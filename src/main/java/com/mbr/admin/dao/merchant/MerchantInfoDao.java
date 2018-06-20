package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.MerchantInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MerchantInfoDao extends TkMapper<MerchantInfo> {

    @Select("<script>" +
            "select * from merchant_info"+
            /*"<where>" +
                "<if test=\"nameSearch!=null and nameSearch!=''\">" +
                    "and name like '%${nameSearch}%'"+
                "</if>"+
            "</where>"+*/
            "</script>")
    public List<MerchantInfo> queryList(@Param("nameSearch") String nameSearch);

    //修改商户表的渠道号
    @Update("update merchant_info set channel = #{channel},audit=1 where id=#{id}")
    public int updateMerchantInfoChannel(@Param("channel") String channel,@Param("id")String id);

    //修改商户资源表的渠道号
    @Update("update merchant_vs_resource set channel=#{channel} where merchant_id = #{id}")
    public int updateMerchantInfoResourceChannel(@Param("channel") String channel,@Param("id")String id);

    @Update("update merchant_info set status=1 where id=#{id}")
    public int updateMerchantInfoForStatus(@Param("id")String id);

    @Select("select rsa_public from merchant_info where id=#{id}")
    public String queryRsaPublic(@Param("id")String id);

    @Select("select rsa_private from merchant_info where id=#{id}")
    public String queryRsaPrivate(@Param("id")String id);

    //查看商户资源表中有无对应的商户
    @Select("select * from merchant_vs_resource where merchant_id=#{id}")
    public List<MerchantInfo> queryMerchantInfoFromMVR(@Param("id")String id);
}
