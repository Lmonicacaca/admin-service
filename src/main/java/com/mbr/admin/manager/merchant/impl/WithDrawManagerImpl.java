package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.dao.merchant.WithDrawDao;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.domain.merchant.WithDraw;
import com.mbr.admin.manager.merchant.WithDrawManager;
import com.mbr.admin.repository.ChannelRepository;
import com.mbr.admin.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WithDrawManagerImpl implements WithDrawManager {

    @Resource
    private WithDrawDao withDrawDao;
    @Resource
    private ChannelRepository channelRepository;
    @Resource
    private ProductRepository productRepository;
    @Override
    public List<Map<String,Object>> queryList(String merchantId,String channel) {
        List<Map<String,Object>> list = new ArrayList<>();
        List<WithDraw> withDrawList = withDrawDao.queryList(merchantId, channel);
        for(int i=0;i<withDrawList.size();i++){
            Product coin = productRepository.findById(withDrawList.get(i).getCoinId());
            Map<String,Object> map = new HashMap<>();
            map.put("id",withDrawList.get(i).getId());
            map.put("createTime",withDrawList.get(i).getCreateTime());
            map.put("address",withDrawList.get(i).getAddress());
            map.put("updateTime",withDrawList.get(i).getUpdateTime());
            map.put("merchantId",withDrawList.get(i).getMerchantId());
            map.put("coinId",withDrawList.get(i).getCoinId());
            map.put("coinName",coin.getCoinName());
            map.put("tokenAddress",coin.getTokenAddress());
            map.put("channel",withDrawList.get(i).getChannel());
            map.put("status",withDrawList.get(i).getStatus());
            list.add(map);
        }
        return list;
    }

    @Override
    public void deleteById(Long id) {
         withDrawDao.deleteById(id);
    }

    @Override
    public Map<String,Object> selectById(Long id) {
        WithDraw withDraw = withDrawDao.selectById(id);
        Long coinId = withDraw.getCoinId();
        Product coin = productRepository.findById(coinId);
        Map<String,Object> map = new HashMap<>();
        map.put("id",withDraw.getId());
        map.put("createTime",withDraw.getCreateTime());
        map.put("address",withDraw.getAddress());
        map.put("updateTime",withDraw.getUpdateTime());
        map.put("merchantId",withDraw.getMerchantId());
        map.put("coinId",withDraw.getCoinId());
        map.put("coinName",coin.getCoinName());
        map.put("tokenAddress",coin.getTokenAddress());
        map.put("channel",withDraw.getChannel());
        map.put("status",withDraw.getStatus());
        return map;
    }

    @Override
    public List<Map<String, Object>> queryChannel() {
        List<Channel> channelList = channelRepository.findAllByStatus(0);
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<channelList.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",channelList.get(i).getId());
            map.put("text",channelList.get(i).getId());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryCoin() {

        List<Product> productList = productRepository.findAllByOnlineStatus(0);
        List<Map<String,Object>> list = new ArrayList<>();
        for(int j=0;j<productList.size();j++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",productList.get(j).getId());
            map.put("text",productList.get(j).getCoinName());
            list.add(map);
        }
        return list;
    }

    @Override
    public int updateById(WithDraw withDraw) {
        return withDrawDao.updateByPrimaryKey(withDraw);
    }

    @Override
    public int saveWithDraw(WithDraw withDraw) {
        WithDraw withDrawExit = withDrawDao.selectByAddrAndCoinId(withDraw.getAddress(), withDraw.getCoinId());
        if(withDrawExit != null){
            return 0;
        }
        withDrawDao.insert(withDraw);
        return 1;
    }
}
