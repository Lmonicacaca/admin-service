package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.MerchantVsResource;
import com.mbr.admin.domain.merchant.Vo.MerchantVsResourceVo;

import java.util.List;
import java.util.Map;

public interface MerchantVsResourceManager {

    public List<MerchantVsResourceVo> queryList(String merchantId);

    public int deleteMerchantVsResource(String id);

    public List<Map<String,Object>> queryAllUrl();

    public List<Map<String,Object>> queryChannel();

    public int insertMerchantVsResource(MerchantVsResource merchantVsResource);

}
