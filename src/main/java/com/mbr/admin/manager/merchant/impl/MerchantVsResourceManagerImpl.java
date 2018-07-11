package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.dao.merchant.MerchantVsResourceDao;
import com.mbr.admin.domain.merchant.MerchantInfo;
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
    @Resource
    private MerchantInfoDao merchantInfoDao;
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
    @Transactional(rollbackFor = MerchantException.class)
    public int insertMerchantVsResource(MerchantVsResourceVo merchantVsResourceVo) throws MerchantException {
        //判断权限是否已存在
        String resourceIdList = merchantVsResourceVo.getResourceIdList();
        String[] resourceIds = resourceIdList.split(",");
        for(int i=0;i<resourceIds.length;i++){
            if(merchantVsResourceDao.queryMerchantVsResourceByCondition(merchantVsResourceVo.getMerchantId(),Long.parseLong(resourceIds[i]))!=null){
                return 999;
            }
        }
        int count = 0;
        for(int i=0;i<resourceIds.length;i++){
            MerchantVsResource merchantVsResource = new MerchantVsResource();
            merchantVsResource.setId(new TimestampPkGenerator().next(getClass())+"");
            merchantVsResource.setCreateTime(new Date());
            SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            merchantVsResource.setCreateUserName(securityUserDetails.getUsername());
            merchantVsResource.setMerchantId(merchantVsResourceVo.getMerchantId());
            merchantVsResource.setStatus(0);
            merchantVsResource.setChannel(merchantVsResourceVo.getChannel());
            merchantVsResource.setResourceId(Long.parseLong(resourceIds[i]));
            int k = merchantVsResourceDao.insertMerchantVsResource(merchantVsResource);
            if(k>0){
                count++;
            }
        }
        if(count==resourceIds.length){
            return 1;
        }else{
            throw new MerchantException("权限添加失败");
        }
    }

    @Override
    public List<Map<String, Object>> queryMerchantId() {

        List<MerchantInfo> merchantInfoList = merchantInfoDao.selectAll();
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<merchantInfoList.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",merchantInfoList.get(i).getId());
            map.put("text",merchantInfoList.get(i).getId());
            list.add(map);
        }
        return list;
    }
}
