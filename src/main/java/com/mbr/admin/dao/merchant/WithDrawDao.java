package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.Vo.WithDrawVo;
import com.mbr.admin.domain.merchant.WithDraw;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WithDrawDao extends TkMapper<WithDraw> {

    @Select("<script>" +
            "select w.*,m.name as merchantName from withdraw as w,merchant_info as m where w.merchant_id=m.id "+
            "<if test=\"merchantId!=null and merchantId !=''\">" +
            " and w.merchant_id=#{merchantId}"+
            "</if>"+
            "<if test=\"merchantName!=null and merchantName !=''\">" +
            " and m.name like '%${merchantName}%'"+
            "</if>"+
            "order by create_time desc,update_time desc"+
            "</script>")
    public List<WithDrawVo> queryList(@Param(value = "merchantId") String merchantId, @Param(value = "merchantName")String merchantName);


    @Delete("delete from withdraw where id=#{id}")
    public void deleteById(@Param(value = "id")Long id);

    @Select("select * from withdraw where id=#{id}")
    public WithDraw selectById(Long id);

    @Select("select * from withdraw where address=#{address} and coin_id=#{coinId}")
    public WithDraw selectByAddrAndCoinId(@Param(value = "address")String address,@Param(value = "coinId")Long coinId);


    @Insert("insert into withdraw values(#{withDraw.id},#{withDraw.createTime},#{withDraw.address},null,null,#{withDraw.merchantId},#{withDraw.coinId},#{withDraw.status},#{withDraw.channel})")
    public int insertWithdraw(@Param("withDraw") WithDraw withDraw);

    @Select("select * from withdraw where merchant_id=#{merchantId} and coin_id=#{coinId}")
    public WithDraw queryWithdraw(@Param("merchantId")String merchantId,@Param("coinId")Long coinId);

    @Update("update withdraw set channel=#{channel},status=1,address = #{address} where merchant_id=#{merchantId} and coin_id=#{coinId}")
    public int updateWithdraw(@Param("merchantId")String merchantId,@Param("coinId")Long coinId,@Param("channel")Long channel,@Param("address")String address);

    @Update("update withdraw set create_time=#{withDraw.createTime},address = #{withDraw.address},update_time=#{withDraw.updateTime},merchant_id=#{withDraw.merchantId},coin_id=#{withDraw.coinId},status=#{withDraw.status},channel=#{withDraw.channel} where id=#{withDraw.id}")
    public int updateById(@Param("withDraw")WithDraw withDraw);

    @Select("select * from withdraw where merchant_id=#{merchantId} and coin_id=#{coinId}")
    public WithDraw selectByMerchantIdAndCoinId(@Param("merchantId")String merchantId,@Param("coinId")Long coinId);

    @Delete("delete from withdraw where merchant_id = #{merchantId} and channel = #{channel}")
    public int deleteByMerchantIdAndChannel(@Param("merchantId")String merchantId,@Param("channel")Long channel);
}
