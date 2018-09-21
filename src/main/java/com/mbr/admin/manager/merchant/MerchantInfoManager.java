package com.mbr.admin.manager.merchant;

import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.Vo.MerchantInfoVo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MerchantInfoManager {

    public List<MerchantInfoVo> queryList(String nameSearch,String idSearch);

    public List<Map<String,Object>> queryChannel();

    @Transactional
    public boolean deleteMerchantInfo(String id) throws MerchantException;

    public String queryRsaPublic(String id);

    public String queryRsaPrivate(String id);

    public MerchantInfo queryById(String id);

    public int updateChannelById(Long channel,String id);

    public List<Map<String,Object>> queryIsShow();

    public String addOrUpdate(MerchantInfoVo merchantInfoVo, HttpServletRequest request) throws MerchantException;

    public String auditMerchant(MerchantInfo merchantInfo);
}
