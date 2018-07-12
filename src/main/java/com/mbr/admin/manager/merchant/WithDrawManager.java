package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.Vo.WithDrawVo;
import com.mbr.admin.domain.merchant.WithDraw;

import java.util.List;
import java.util.Map;

public interface WithDrawManager {
    public List<WithDrawVo> queryList(String merchantId, String merchantName);

    public void deleteById(Long id);

    public Object selectById(Long id);


    public List<Map<String,Object>> queryCoin();

    public int updateById(WithDraw withDraw);

    public List<Map<String,Object>> queryMerchant();


    public String addOrUpdate(WithDraw withDraw);
}
