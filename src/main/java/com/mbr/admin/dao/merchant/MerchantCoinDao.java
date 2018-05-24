package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.MerchantCoin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantCoinDao extends TkMapper<MerchantCoin> {

  @Select("<script>" +
            "select * from merchant_coin "+
            "<where>"+
                "<if test=\"merchantId!=null and merchantId!=''\">" +
                    " and merchant_id=#{merchantId}"+
                "</if>"+
                "<if test=\"channel!=null and channel!=''\">" +
                " and channel=#{channel}"+
                "</if>"+
                "and status=0"+
            "</where>"+
            "</script>")
    public List<MerchantCoin> queryList(@Param(value = "merchantId") String merchantId, @Param(value = "channel")String channel);

    @Select("update merchant_coin set status=#{status} where id=#{id}")
    void updateById(MerchantCoin merchantCoin);

    @Select("select count(*) from merchant_coin")
    public int getCount();



}
