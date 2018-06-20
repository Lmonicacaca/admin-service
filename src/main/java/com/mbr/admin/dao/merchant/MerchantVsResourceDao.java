package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.MerchantVsResource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantVsResourceDao extends TkMapper<MerchantVsResource> {

    @Insert("<script>" +
            "insert into merchant_vs_resource values"+
            "<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">" +
                "(null,#{item.merchantId},#{item.resourceId},#{item.status},null,null,null,null,#{item.channel})"+
            "</foreach>"+
            "</script>")
    public int insertList(@Param("list") List<MerchantVsResource> list);
 }
