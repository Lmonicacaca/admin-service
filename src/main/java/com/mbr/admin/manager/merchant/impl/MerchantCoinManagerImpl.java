package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.dao.merchant.MerchantCoinDao;
import com.mbr.admin.dao.system.SysUsersDao;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.MerchantCoin;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.domain.system.SysUsers;
import com.mbr.admin.manager.merchant.MerchantCoinManager;
import com.mbr.admin.repository.ChannelRepository;
import com.mbr.admin.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerchantCoinManagerImpl implements MerchantCoinManager {

    @Resource
    private MerchantCoinDao merchantCoinDao;

    @Resource
    private ProductRepository productRepository;
    @Resource
    private ChannelRepository channelRepository;
    @Resource
    private SysUsersDao sysUsersDao;
    @Override
    public List<MerchantCoin> queryList(String merchantId,String channel) {

        return merchantCoinDao.queryList(merchantId,channel);
    }

    @Override
    public MerchantCoin selectById(Long id) {
        return merchantCoinDao.selectByPrimaryKey(id);
    }

    @Override
    public void updateById(MerchantCoin merchantCoin) {
        merchantCoinDao.updateById(merchantCoin);
    }

    @Override
    public List<Map<String,String>> findAllProduct() {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        List<Product> productList = productRepository.findAllByOnlineStatus(0);
        for(int i=0;i<productList.size();i++){
            Map<String,String> map = new HashMap<>();
            map.put("id",productList.get(i).getId());
            map.put("text",productList.get(i).getCoinName());
            list.add(map);
        }

        return list;
    }

    @Override
    public List<Map<String, String>> findAllChannel() {
        List<Map<String,String>> list = new ArrayList<>();
        List<Channel> channelList = channelRepository.findAllByStatus(0);
        for(int i=0;i<channelList.size();i++){
            Map<String,String> map = new HashMap<>();
            map.put("id",channelList.get(i).getId());
            map.put("text",channelList.get(i).getId());
            list.add(map);
        }
        return list;
    }

    @Override
    public Product findCoinById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public int updataMerchantCoin(MerchantCoin merchantCoin) {
        return merchantCoinDao.updateByPrimaryKey(merchantCoin);
    }

    @Override
    public List<Map<String ,Object>> queryUser() {

        List<Map<String ,Object>> list = new ArrayList<>();
        List<SysUsers> sysUsersList = sysUsersDao.selectAll();
        for(int i=0;i<sysUsersList.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",sysUsersList.get(i).getUsername());
            map.put("text",sysUsersList.get(i).getUsername());
            list.add(map);
        }
        return list;
    }

    @Override
    public int getMerchantCoinCount() {

        return merchantCoinDao.getCount();
    }

    @Override
    public int saveMerchantCoin(MerchantCoin merchantCoin) {
        return merchantCoinDao.insert(merchantCoin);
    }


}
