package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.WithDraw;

import java.util.List;
import java.util.Map;

public interface WithDrawManager {
    public List<Map<String,Object>> queryList(String merchantId,String channel);

    public void deleteById(Long id);

    public Map<String,Object> selectById(Long id);

    public List<Map<String,Object>> queryChannel();

    public List<Map<String,Object>> queryCoin();

    public int updateById(WithDraw withDraw);

    public int saveWithDraw(WithDraw withDraw);
}
