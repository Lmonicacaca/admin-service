package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.dao.merchant.MerchantVsResourceDao;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.MerchantVsResource;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.manager.merchant.MerchantInfoManager;
import com.mbr.admin.repository.ChannelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class MercahntInfoManagerImpl implements MerchantInfoManager {

    @Resource
    private MerchantInfoDao merchantInfoDao;
    @Resource
    private ChannelRepository channelRepository;

    @Resource
    private ChannelManager channelManager;
    @Resource
    private MerchantVsResourceDao merchantVsResourceDao;

    @Override
    public List<MerchantInfo> queryList(String nameSearch) {
        return merchantInfoDao.queryList(nameSearch);
    }

    @Override
    public List<Map<String, Object>> queryChannel() {
        return channelManager.findAllChannel();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String saveChannelForMerchant(MerchantInfo merchantInfo) throws Exception{

        Channel channel = new Channel();
        String channelId = new TimestampPkGenerator().next(getClass()).toString();
        String channelNumber = new TimestampPkGenerator().next(getClass()).toString();
        channel.setId(channelId);
        channel.setChannel(channelNumber);
        channel.setSystemName(merchantInfo.getName());
        channel.setStatus(0);
        channel.setMerchantId(merchantInfo.getId());
        channel.setCreateTime(new Date());
/*
            //添加商户的渠道号信息
            int i = merchantInfoDao.updateMerchantInfoChannel(channelNumber, merchantInfo.getId());
            if(i>0){
                List<MerchantInfo> merchantInfoList = merchantInfoDao.queryMerchantInfoFromMVR(merchantInfo.getId());
                int k = 0;
                if(merchantInfoList.size()>0){
                    //修改商户资源表中的channel信息
                    k = merchantInfoDao.updateMerchantInfoResourceChannel(channelNumber, merchantInfo.getId());
                }else{
                    //在商户资源表中添加信息
                    k = initMerchantVsResource(merchantInfo.getId(), channelNumber);
                }

                if(k>0){
                    Channel channelInsert = channelRepository.insert(channel);
                    if(channelInsert!=null){
                        return "success";
                    }else{
                        return null;
                    }

                }else{
                    throw new Exception("商户资源表更新渠道号失败");
                }
            }else{
                throw new Exception("商户表更新渠道号失败");
            }
*/
        return null;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String updateChannelForMerchant(MerchantInfo merchantInfo) throws Exception{

       /* //添加商户的渠道号信息
        int i = merchantInfoDao.updateMerchantInfoChannel(merchantInfo.getChannel(), merchantInfo.getId());
        if(i>0){
            List<MerchantInfo> merchantInfoList = merchantInfoDao.queryMerchantInfoFromMVR(merchantInfo.getId());
            int k = 0;
            if(merchantInfoList.size()>0){
                //修改商户资源表中的channel信息
                k = merchantInfoDao.updateMerchantInfoResourceChannel(merchantInfo.getChannel(), merchantInfo.getId());
            }else{
                //在商户资源表中添加信息
                k = initMerchantVsResource(merchantInfo.getId(), merchantInfo.getChannel());
            }
            if(k>0){
                return "success";
            }else{
                throw new Exception("商户资源表更新渠道号失败");
            }
        }else{
            throw new Exception("商户表更新渠道号失败");
        }*/

return null;
    }

    @Override
    public int deleteMerchantInfo(String id) {
        return merchantInfoDao.updateMerchantInfoForStatus(id);
    }

    @Override
    public String queryRsaPublic(String id) {
        return merchantInfoDao.queryRsaPublic(id);
    }

    @Override
    public String queryRsaPrivate(String id) {
        return merchantInfoDao.queryRsaPrivate(id);
    }

    @Override
    public MerchantInfo queryById(String id) {

        return merchantInfoDao.queryById(id);
    }

    @Override
    public int updateChannelById(String channel,String id) {
        return merchantInfoDao.updateMerchantInfoChannel(channel,id);
    }


}
