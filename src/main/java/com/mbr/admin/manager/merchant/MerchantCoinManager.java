package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.MerchantCoin;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.domain.merchant.Vo.MerchantCoinVo;
import com.mbr.admin.domain.system.SysUsers;

import java.util.List;
import java.util.Map;

public interface MerchantCoinManager {

    public List<MerchantCoinVo> queryList(String merchantId, String nameSearch);


    public MerchantCoinVo selectById(String id);


    public int deleteById(Long id);

    public List<Map<String,String>> findAllProduct();

    public List<Map<String,Object>> findAllChannel();

    public List<Map<String,Object>> queryStatus();

    public List<Map<String,Object>> queryMerchantId();

    public String addOrUpdate(MerchantCoinVo merchantCoinVo);



}
