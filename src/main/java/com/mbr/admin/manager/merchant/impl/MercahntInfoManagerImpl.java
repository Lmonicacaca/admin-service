package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.MerchantInfo;
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

    @Override
    public List<MerchantInfo> queryList(String nameSearch) {
        return merchantInfoDao.queryList(nameSearch);
    }

    @Override
    public List<Map<String, Object>> queryChannel() {
        List<Channel> channelList = channelRepository.findAllByStatus(0);
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<channelList.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",channelList.get(i).getId());
            map.put("text",channelList.get(i).getChannel());
            list.add(map);
        }
        return list;
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
        //添加渠道号到channel
//        Channel channelInsert = channelRepository.insert(channel);
            //添加商户的渠道号信息
            int i = merchantInfoDao.updateMerchantInfoChannel(channelNumber, merchantInfo.getId());
            if(i>0){
                //添加商户资源表中的channel信息
                int k = merchantInfoDao.updateMerchantInfoResourceChannel(channelNumber, merchantInfo.getId());
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

    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String updateChannelForMerchant(MerchantInfo merchantInfo) throws Exception{

        Long channelId= Long.parseLong(merchantInfo.getChannel());
        Channel channel = channelRepository.findOne(channelId);

        //添加商户的渠道号信息
        int i = merchantInfoDao.updateMerchantInfoChannel(channel.getChannel(), merchantInfo.getId());
        if(i>0){
            //添加商户资源表中的channel信息
            int k = merchantInfoDao.updateMerchantInfoResourceChannel(channel.getChannel(), merchantInfo.getId());
            if(k>0){
                return "success";
            }else{
                throw new Exception("商户资源表更新渠道号失败");
            }
        }else{
            throw new Exception("商户表更新渠道号失败");
        }


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
}
