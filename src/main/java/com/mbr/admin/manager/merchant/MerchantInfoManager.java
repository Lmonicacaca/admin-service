package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.Vo.MerchantInfoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MerchantInfoManager {

    public List<MerchantInfo> queryList(String nameSearch,String idSearch);

    public List<Map<String,Object>> queryChannel();

    public int deleteMerchantInfo(String id);

    public String queryRsaPublic(String id);

    public String queryRsaPrivate(String id);

    public MerchantInfo queryById(String id);

    public int updateChannelById(String channel,String id);

    public List<Map<String,Object>> queryIsShow();

    public List<Map<String,Object>> queryStatus();

    public String addOrUpdate(MerchantInfoVo merchantInfoVo, HttpServletRequest request);

}
