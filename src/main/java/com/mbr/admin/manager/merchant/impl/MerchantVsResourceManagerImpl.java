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
    public int insertMerchantVsResource(MerchantVsResourceVo merchantVsResourceVo){
        Long channel = merchantInfoDao.selectChannelByMerchantId(merchantVsResourceVo.getMerchantId());
        //判断权限是否已存在
        String resourceIdList = merchantVsResourceVo.getResourceIdList();
        String[] resourceIds = resourceIdList.split(",");
        StringBuffer insertResourceIds = new StringBuffer();
        for(int i=0;i<resourceIds.length;i++){
            if(merchantVsResourceDao.queryMerchantVsResourceByCondition(merchantVsResourceVo.getMerchantId(),Long.parseLong(resourceIds[i]))==null){
                insertResourceIds.append(resourceIds[i]).append(",");
            }
        }
        if(insertResourceIds.toString().equals("")){
            return 999;
        }
        String[] insertIds = insertResourceIds.toString().split(",");

        int count = 0;
        for(int i=0;i<insertIds.length;i++){
            MerchantVsResource merchantVsResource = new MerchantVsResource();
            merchantVsResource.setId(new TimestampPkGenerator().next(getClass())+"");
            merchantVsResource.setCreateTime(new Date());
            SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            merchantVsResource.setCreateUserName(securityUserDetails.getUsername());
            merchantVsResource.setMerchantId(merchantVsResourceVo.getMerchantId());
            merchantVsResource.setStatus(0);
            merchantVsResource.setChannel(channel);
            merchantVsResource.setResourceId(Long.parseLong(insertIds[i]));
            int k = merchantVsResourceDao.insertMerchantVsResource(merchantVsResource);
            if(k>0){
                count++;
            }
        }
        if(count==insertIds.length){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public List<Map<String, Object>> queryMerchantId() {

        List<MerchantInfo> merchantInfoList = merchantInfoDao.selectAll();
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<merchantInfoList.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",merchantInfoList.get(i).getId());
            map.put("text",merchantInfoList.get(i).getName());
            list.add(map);
        }
        return list;
    }
}
