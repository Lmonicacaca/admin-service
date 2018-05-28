package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.WithDraw;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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


    @Select("update withdraw set status=1 where id=#{id}")
    public void updateStatusById(@Param(value = "id")Long id);

    @Select("select * from withdraw where id=#{id}")
    public WithDraw selectById(Long id);

    @Select("select * from withdraw where address=#{address} and coin_id=#{coinId}")
    public WithDraw selectByAddrAndCoinId(@Param(value = "address")String address,@Param(value = "coinId")Long coinId);


}
