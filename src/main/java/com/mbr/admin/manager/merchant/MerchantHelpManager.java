package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.Vo.MerchantHelpVo;

import java.util.List;

public interface MerchantHelpManager{

    public List<MerchantHelpVo> queryList(String title);

    public String queryContent(Integer id);

    public int deleteMerchantHelp(Integer id);
}
