package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.MerchantCoinTmp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MerchantCoinTmpDao extends TkMapper<MerchantCoinTmp> {

    @Select("<script>" +
            "select * from merchant_coin_tmp where status in (0,2)"+
            "<if test=\"merchantIdSearch!=null and merchantIdSearch!=''\">" +
            " and merchant_id=#{merchantIdSearch}"+
            "</if>"+
            "</script>")
    public List<MerchantCoinTmp> queryList(@Param("merchantIdSearch") String merchantIdSearch);

    @Select("select * from merchant_coin_tmp where id=#{id}")
    public MerchantCoinTmp queryById(@Param("id")String id);

    @Update("update merchant_coin_tmp set status=#{status},channel=#{channel} where id=#{id}")
    public int updateStatusByAudit(@Param("status")int status,@Param("id")String id,@Param("channel")Long channel);

    @Update("update merchant_coin_tmp set status=#{status} where id=#{id}")
    public int updateStatusNotPass(@Param("status")int status,@Param("id")String id);
}
