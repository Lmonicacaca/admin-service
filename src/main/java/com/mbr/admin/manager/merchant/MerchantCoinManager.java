package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.MerchantCoin;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.domain.system.SysUsers;

import java.util.List;
import java.util.Map;

public interface MerchantCoinManager {

    public List<MerchantCoin> queryList(String merchantId,String channel);


    public MerchantCoin selectById(Long id);

    public void updateById(MerchantCoin merchantCoin);

    public List<Map<String,String>> findAllProduct();

    public List<Map<String,String>> findAllChannel();

    public Product findCoinById(Long id);

    public int updataMerchantCoin(MerchantCoin merchantCoin);

    public List<Map<String ,Object>> queryUser();

    public int getMerchantCoinCount();

    public int saveMerchantCoin(MerchantCoin merchantCoin);

}
