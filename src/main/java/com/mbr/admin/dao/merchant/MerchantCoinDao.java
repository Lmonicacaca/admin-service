package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.MerchantCoin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MerchantCoinDao extends TkMapper<MerchantCoin> {

  @Select("<script>" +
            "select * from merchant_coin "+
            "<where>"+
                "<if test=\"merchantId!=null and merchantId !=''\">" +
                    " and merchant_id=#{merchantId}"+
                "</if>"+
                "<if test=\"channel!=null and channel !=''\">" +
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

    @Select("select * from merchant_coin where coin_id=#{coinId} and address=#{address}")
    public MerchantCoin selectByAddressAndCoinId(@Param(value = "address") String address,@Param(value = "coinId")Long coinId);

    @Insert("insert into merchant_coin values(#{merchantCoin.id},#{merchantCoin.coinId},#{merchantCoin.merchantId},#{merchantCoin.status},#{merchantCoin.createUserName},null,#{merchantCoin.createTime},null,#{merchantCoin.address},#{merchantCoin.tokenAddress},#{merchantCoin.coinName},#{merchantCoin.channel})")
    public int insertMerchantCoin(@Param("merchantCoin") MerchantCoin merchantCoin);

    @Select("select * from merchant_coin where coin_id=#{coinId} and merchant_id=#{merchantId}")
    public MerchantCoin queryCoin(@Param("merchantId")String merchantId,@Param("coinId")String coinId);

    @Update("update merchant_coin set channel=#{channel},address=#{address} where merchant_id=#{merchantId} and coin_id=#{coinId}")
    public int updateCoin(@Param("merchantId")String merchantId,@Param("channel")String channel,@Param("coinId")String coinId,@Param("address")String address);

}
