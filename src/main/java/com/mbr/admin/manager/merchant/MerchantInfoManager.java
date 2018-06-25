package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.MerchantInfo;

import java.util.List;
import java.util.Map;

public interface MerchantInfoManager {

    public List<MerchantInfo> queryList(String nameSearch);

    public List<Map<String,Object>> queryChannel();

    public int deleteMerchantInfo(String id);

    public String queryRsaPublic(String id);

    public String queryRsaPrivate(String id);

    public MerchantInfo queryById(String id);

    public int updateChannelById(String channel,String id);
}
