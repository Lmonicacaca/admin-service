package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.MerchantInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MerchantInfoDao extends TkMapper<MerchantInfo> {

    @Select("<script>" +
            "select * from merchant_info "+
            "<where>" +
                "<if test=\"nameSearch!=null and nameSearch!=''\">" +
                    " and name like '%${nameSearch}%'"+
                "</if>"+
                "<if test=\"idSearch!=null and idSearch!=''\">" +
                " and id = #{idSearch}"+
                "</if>"+
            "</where>"+
            "order by create_time desc"+
            "</script>")
    public List<MerchantInfo> queryList(@Param("nameSearch") String nameSearch,@Param("idSearch")String idSearch);

    //修改商户表的渠道号
    @Update("update merchant_info set channel = #{channel},status=1 where id=#{id}")
    public int updateMerchantInfoChannel(@Param("channel") Long channel,@Param("id")String id);

    @Delete("delete from merchant_info where id=#{id}")
    public int deleteById(@Param("id")String id);

    @Select("select rsa_public from merchant_info where id=#{id}")
    public String queryRsaPublic(@Param("id")String id);

    @Select("select rsa_private from merchant_info where id=#{id}")
    public String queryRsaPrivate(@Param("id")String id);

    @Select("select * from merchant_info where id=#{id}")
    public MerchantInfo queryById(@Param("id")String id);


    @Update("update merchant_info set name=#{merchantInfo.name},logo_bill = #{merchantInfo.logoBill},description=#{merchantInfo.description},website = #{merchantInfo.website},address=#{merchantInfo.address},create_time =#{merchantInfo.createTime},update_time=#{merchantInfo.updateTime},create_user_name=#{merchantInfo.createUserName},update_user_name=#{merchantInfo.updateUserName},status=#{merchantInfo.status},logo_intro=#{merchantInfo.logoIntro},rsa_public =#{merchantInfo.rsaPublic},rsa_private=#{merchantInfo.rsaPrivate},is_show=#{merchantInfo.isShow},channel=#{merchantInfo.channel} where id=#{merchantInfo.id}")
    public int updateById(@Param("merchantInfo")MerchantInfo merchantInfo);

   /* @Update("update merchant_info set name=#{name} where id=#{id}")
    public int updateNameByMerchantId(@Param("id")String id,@Param("name")String name);
*/
    @Select("select * from merchant_info")
    public List<MerchantInfo> selectAll();

    @Select("select channel from merchant_info where id=#{id}")
    public Long selectChannelByMerchantId(@Param("id")String id);

}
