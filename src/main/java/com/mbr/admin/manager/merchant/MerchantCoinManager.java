package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.MerchantCoin;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.domain.system.SysUsers;

import java.util.List;
import java.util.Map;

public interface MerchantCoinManager {

    public List<MerchantCoin> queryList(String merchantId,String channel);


    public MerchantCoin selectById(Long id);


    public int deleteById(Long id);

    public List<Map<String,String>> findAllProduct();

    public List<Map<String,Object>> findAllChannel();

    public Product findCoinById(Long id);

    public int updataMerchantCoin(MerchantCoin merchantCoin);

    public List<Map<String ,Object>> queryUser();


    public int saveMerchantCoin(MerchantCoin merchantCoin);

    public MerchantCoin selectMerchantCoinByAddrAndCoinId(String Address,Long coinId);

}
