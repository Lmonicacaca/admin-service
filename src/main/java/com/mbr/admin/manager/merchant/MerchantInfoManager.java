package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.MerchantInfo;

import java.util.List;
import java.util.Map;

public interface MerchantInfoManager {

    public List<MerchantInfo> queryList(String nameSearch);

    public List<Map<String,Object>> queryChannel();

    //需要生成渠道号
    public String saveChannelForMerchant(MerchantInfo merchantInfo) throws Exception;

    //使用已有的渠道号
    public String updateChannelForMerchant(MerchantInfo merchantInfo) throws Exception;

    public int deleteMerchantInfo(String id);

    public String queryRsaPublic(String id);

    public String queryRsaPrivate(String id);
}
