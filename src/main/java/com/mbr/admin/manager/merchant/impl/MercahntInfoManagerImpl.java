package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.manager.merchant.MerchantInfoManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MercahntInfoManagerImpl implements MerchantInfoManager {

    @Resource
    private MerchantInfoDao merchantInfoDao;

    @Resource
    private ChannelManager channelManager;

    @Override
    public List<MerchantInfo> queryList(String nameSearch) {
        return merchantInfoDao.queryList(nameSearch);
    }

    @Override
    public List<Map<String, Object>> queryChannel() {
        return channelManager.findAllChannel();
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
