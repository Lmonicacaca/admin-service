package com.mbr.admin.manager.merchant;

import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.merchant.MerchantCoinTmp;

import java.util.List;
import java.util.Map;

public interface MerchantCoinTmpManager {

    public List<MerchantCoinTmp> queryList(String merchantIdSearch);

    public List<Map<String,Object>> queryChannel();

    public MerchantCoinTmp queryById(String id);

    public String auditMerchant(MerchantCoinTmp merchantCoinTmp) throws MerchantException;


    public int auditMercahntNotPass(String id,int status);

}
