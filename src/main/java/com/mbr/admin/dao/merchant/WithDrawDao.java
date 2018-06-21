package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.WithDraw;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WithDrawDao extends TkMapper<WithDraw> {

    @Select("<script>" +
            "select * from withdraw "+
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
    public List<WithDraw> queryList(@Param(value = "merchantId") String merchantId, @Param(value = "channel")String channel);


    @Delete("delete from withdraw where id=#{id}")
    public void deleteById(@Param(value = "id")Long id);

    @Select("select * from withdraw where id=#{id}")
    public WithDraw selectById(Long id);

    @Select("select * from withdraw where address=#{address} and coin_id=#{coinId}")
    public WithDraw selectByAddrAndCoinId(@Param(value = "address")String address,@Param(value = "coinId")Long coinId);


    @Insert("insert into withdraw values(#{withDraw.id},#{withDraw.createTime},#{withDraw.address},null,null,#{withDraw.merchantId},#{withDraw.coinId},#{withDraw.status},#{withDraw.channel})")
    public int insertWithdraw(@Param("withDraw") WithDraw withDraw);

    @Select("select * from withdraw where merchant_id=#{merchantId} and coin_id=#{coinId}")
    public WithDraw queryWithdraw(@Param("merchantId")String merchantId,@Param("coinId")String coinId);

    @Update("update withdraw set channel=#{channel},address = #{address} where merchant_id=#{merchantId} and coin_id=#{coinId}")
    public int updateWithdraw(@Param("merchantId")String merchantId,@Param("coinId")String coinId,@Param("channel")String channel,@Param("address")String address);

}
