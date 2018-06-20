package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.dao.merchant.MerchantVsResourceDao;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.MerchantVsResource;
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
    private MerchantVsResourceDao merchantVsResourceDao;

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

    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String updateChannelForMerchant(MerchantInfo merchantInfo) throws Exception{

        //添加商户的渠道号信息
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

    /**
     * 为新建的商家初始化资源
     *
     * @param merchantId
     */
    private int initMerchantVsResource(String merchantId, String channelId) {

        final MerchantVsResource re3 = new MerchantVsResource();
        re3.setMerchantId(merchantId);
        re3.setChannel(channelId);
        re3.setResourceId("3");
        re3.setStatus(0);
        final MerchantVsResource re4 = new MerchantVsResource();
        re4.setMerchantId(merchantId);
        re4.setChannel(channelId);
        re4.setResourceId("4");
        re4.setStatus(0);
        final MerchantVsResource re11 = new MerchantVsResource();
        re11.setMerchantId(merchantId);
        re11.setChannel(channelId);
        re11.setResourceId("11");
        re11.setStatus(0);
        final MerchantVsResource re12 = new MerchantVsResource();
        re12.setMerchantId(merchantId);
        re12.setChannel(channelId);
        re12.setResourceId("12");
        re12.setStatus(0);
        final MerchantVsResource re22 = new MerchantVsResource();
        re22.setMerchantId(merchantId);
        re22.setChannel(channelId);
        re22.setResourceId("22");
        re22.setStatus(0);
        final MerchantVsResource re23 = new MerchantVsResource();
        re23.setMerchantId(merchantId);
        re23.setChannel(channelId);
        re23.setResourceId("23");
        re23.setStatus(0);
        final MerchantVsResource re24 = new MerchantVsResource();
        re24.setMerchantId(merchantId);
        re24.setChannel(channelId);
        re24.setResourceId("24");
        re24.setStatus(0);

        final List<MerchantVsResource> list = new ArrayList<MerchantVsResource>(7);
        list.add(re3);
        list.add(re4);
        list.add(re11);
        list.add(re12);
        list.add(re22);
        list.add(re23);
        list.add(re24);
        int i = merchantVsResourceDao.insertList(list);
        return i;
    }
}
