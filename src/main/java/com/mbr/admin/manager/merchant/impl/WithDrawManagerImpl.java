package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.dao.merchant.WithDrawDao;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.domain.merchant.Vo.WithDrawVo;
import com.mbr.admin.domain.merchant.WithDraw;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.manager.merchant.WithDrawManager;
import com.mbr.admin.repository.ChannelRepository;
import com.mbr.admin.repository.ProductRepository;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WithDrawManagerImpl implements WithDrawManager {

    @Resource
    private WithDrawDao withDrawDao;
    @Resource
    private ChannelManager channelManager;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private MerchantInfoDao merchantInfoDao;

    @Override
    public List<WithDrawVo> queryList(String merchantId,String merchantName) {
        List<WithDrawVo> withDrawVoList = withDrawDao.queryList(merchantId, merchantName);
        for(int i=0;i<withDrawVoList.size();i++){
            if(withDrawVoList.get(i).getCoinId()!=null&&withDrawVoList.get(i).getCoinId()!=0){
                Product coin = productRepository.findById(withDrawVoList.get(i).getCoinId());
                System.out.println(coin);
                withDrawVoList.get(i).setCoinName(coin.getCoinName());
                withDrawVoList.get(i).setTokenAddress(coin.getTokenAddress());
            }

        }
        return withDrawVoList;
    }

    @Override
    public void deleteById(Long id) {
         withDrawDao.deleteById(id);
    }

    @Override
    public Object selectById(Long id) {

        return withDrawDao.selectById(id);
    }

    @Override
    public List<Map<String, Object>> queryChannel() {

        return channelManager.findAllChannel();
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
    public List<Map<String, Object>> queryMerchant() {
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

    @Override
    public List<Map<String, Object>> queryStatus() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id","0");
        map.put("text","未审核");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id","1");
        map1.put("text","审核通过");
        Map<String,Object> map2 = new HashMap<>();
        map2.put("id","2");
        map2.put("text","审核不通过");
        list.add(map);
        list.add(map1);
        list.add(map2);
        return list;
    }

    @Override
    public String addOrUpdate(WithDraw withDraw) {
      if(withDraw.getStatus()==null){
          withDraw.setStatus(0);
      }
        Long id = null;
        if(withDraw.getId()==null){
            id = new TimestampPkGenerator().next(getClass());
            WithDraw withdrawDistinct = withDrawDao.selectByMerchantIdAndCoinId(withDraw.getMerchantId(), withDraw.getCoinId());
            if(withdrawDistinct!=null){
                return "withdrawExists";
            }
            WithDraw createWithDraw = createWithDraw(withDraw, id);
            System.out.println(createWithDraw);
            int insert = withDrawDao.insert(createWithDraw);
            if(insert>0){
                return "success";
            }else{
                return "insertFailed";
            }
        }else{
            WithDraw createWithDraw = createWithDraw(withDraw, id);
            int i = withDrawDao.updateById(createWithDraw);
            if(i>0){
                return "success";
            }else{
                return "updateFailed";
            }
        }
    }


    public WithDraw createWithDraw(WithDraw withDraw,Long id){
        if(id!=null){
            withDraw.setId(id);
            withDraw.setCreateTime(new Date());
        }else{
            withDraw.setUpdateTime(new Date());
        }
        return withDraw;
    }
}
