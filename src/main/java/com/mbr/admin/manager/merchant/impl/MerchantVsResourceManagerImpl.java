package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.dao.merchant.MerchantVsResourceDao;
import com.mbr.admin.domain.merchant.MerchantResource;
import com.mbr.admin.domain.merchant.MerchantVsResource;
import com.mbr.admin.domain.merchant.Vo.MerchantVsResourceVo;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.manager.merchant.MerchantVsResourceManager;
import com.mbr.admin.manager.security.SecurityUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class MerchantVsResourceManagerImpl implements MerchantVsResourceManager {
    @Resource
    private MerchantVsResourceDao merchantVsResourceDao;
    @Resource
    private ChannelManager channelManager;
    @Override
    public List<MerchantVsResourceVo> queryList(String merchantId) {
        return merchantVsResourceDao.queryList(merchantId);
    }

    @Override
    public int deleteMerchantVsResource(String id) {
        return merchantVsResourceDao.deleteMerchantVsResource(id);
    }

    @Override
    public List<Map<String, Object>> queryAllUrl() {
        List<MerchantResource> merchantResourceList = merchantVsResourceDao.queryAllMerchantResource();
        List<Map<String,Object>> list = new ArrayList<>();
        if(merchantResourceList!=null){
            for(int i=0;i<merchantResourceList.size();i++){
                Map<String,Object> map = new HashMap<>();
                map.put("id",merchantResourceList.get(i).getId());
                map.put("text",merchantResourceList.get(i).getUrl());
                list.add(map);
            }
        }else{
            return null;
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryChannel() {
        return channelManager.findAllChannel();
    }

    @Override
    public int insertMerchantVsResource(MerchantVsResource merchantVsResource) {
        //判断权限是否已存在
        if(merchantVsResourceDao.queryMerchantVsResourceByCondition(merchantVsResource.getMerchantId(),merchantVsResource.getResourceId())!=null){
            return 999;
        }
        merchantVsResource.setCreateTime(new Date());
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        merchantVsResource.setCreateUserName(securityUserDetails.getUsername());
        int i = merchantVsResourceDao.insertMerchantVsResource(merchantVsResource);
        return i;
    }
}
