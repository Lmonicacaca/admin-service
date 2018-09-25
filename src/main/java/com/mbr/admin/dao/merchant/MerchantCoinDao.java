package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.MerchantCoin;
import com.mbr.admin.domain.merchant.Vo.MerchantCoinVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MerchantCoinDao extends TkMapper<MerchantCoin> {

  @Select("<script>" +
            "select mc.*,mi.name as merchantName from merchant_coin as mc,merchant_info as mi where mc.merchant_id=mi.id "+
                "<if test=\"merchantId!=null and merchantId !=''\">" +
                    " and mc.merchant_id=#{merchantId}"+
                "</if>"+
                "<if test=\"nameSearch!=null and nameSearch !=''\">" +
                " and mi.name like '%${nameSearch}%'"+
                "</if>"+
          " order by create_time desc,update_time desc"+
            "</script>")
    public List<MerchantCoinVo> queryList(@Param(value = "merchantId") String merchantId, @Param(value = "nameSearch")String nameSearch);

    @Update("update merchant_coin set coin_id=#{merchantCoin.coinId}, merchant_id=#{merchantCoin.merchantId},status=#{merchantCoin.status},create_user_name=#{merchantCoin.createUserName},update_user_name=#{merchantCoin.updateUserName},create_time=#{merchantCoin.createTime},update_time=#{merchantCoin.updateTime},address=#{merchantCoin.address},token_address=#{merchantCoin.tokenAddress},coin_name=#{merchantCoin.coinName},channel=#{merchantCoin.channel} where id=#{id}")
    public int updateMerchantCoinById(@Param(value = "merchantCoin")MerchantCoin merchantCoin,@Param(value = "id")String id);

    @Select("select count(*) from merchant_coin")
    public int getCount();

    @Select("select * from merchant_coin where coin_id=#{coinId} and merchant_id=#{merchantId}")
    public MerchantCoin selectByMerchantIdAndCoinId(@Param(value = "merchantId") String merchantId,@Param(value = "coinId")Long coinId);

    @Insert("insert into merchant_coin values(#{merchantCoin.id},#{merchantCoin.coinId},#{merchantCoin.merchantId},#{merchantCoin.status},#{merchantCoin.createUserName},null,#{merchantCoin.createTime},null,#{merchantCoin.address},#{merchantCoin.tokenAddress},#{merchantCoin.coinName},#{merchantCoin.channel})")
    public int insertMerchantCoin(@Param("merchantCoin") MerchantCoin merchantCoin);

    @Select("select * from merchant_coin where coin_id=#{coinId} and merchant_id=#{merchantId}")
    public MerchantCoin queryCoin(@Param("merchantId")String merchantId,@Param("coinId")Long coinId);

    @Update("update merchant_coin set channel=#{channel},status=1,address=#{address} where merchant_id=#{merchantId} and coin_id=#{coinId}")
    public int updateCoin(@Param("merchantId")String merchantId,@Param("channel")Long channel,@Param("coinId")Long coinId,@Param("address")String address);

    @Select("select mc.*,mi.name as merchantName from merchant_coin as mc,merchant_info as mi where mc.id=#{id} and mc.merchant_id=mi.id")
    public MerchantCoinVo queryById(@Param("id")String id);

    @Delete("delete from merchant_coin where id=#{id}")
    public int deleteById(@Param("id")Long id);

    @Delete("delete from merchant_coin where merchant_id=#{merchantId} and channel=#{channel}")
    public int deleteByMerchantIdAndChannel(@Param("merchantId")String merchantId,@Param("channel")Long channel);
  @Select("select * from merchant_coin where merchant_id=#{merchantId} and channel=#{channel}")
    public List<MerchantCoin> queryByMerchantIdAndChannel(@Param("merchantId")String merchantId,@Param("channel")Long channel);
}
