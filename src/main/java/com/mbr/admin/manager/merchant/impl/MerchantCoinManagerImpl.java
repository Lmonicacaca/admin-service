package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantCoinDao;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.dao.system.SysUsersDao;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.MerchantCoin;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.domain.merchant.Vo.MerchantCoinVo;
import com.mbr.admin.domain.system.SysUsers;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.manager.merchant.MerchantCoinManager;
import com.mbr.admin.manager.security.SecurityUserDetails;
import com.mbr.admin.repository.ChannelRepository;
import com.mbr.admin.repository.ProductRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MerchantCoinManagerImpl implements MerchantCoinManager {

    @Resource
    private MerchantCoinDao merchantCoinDao;
    @Resource
    private MerchantInfoDao merchantInfoDao;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private ChannelManager channelManager;
    @Override
    public List<MerchantCoinVo> queryList(String merchantId, String nameSearch) {

        return merchantCoinDao.queryList(merchantId,nameSearch);
    }

    @Override
    public MerchantCoinVo selectById(String id) {
        return merchantCoinDao.queryById(id);
    }


    @Override
    public int deleteById(Long id) {
        return merchantCoinDao.deleteById(id);
    }

    @Override
    public List<Map<String,String>> findAllProduct() {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        List<Product> productList = productRepository.findAllByOnlineStatus(0);
        for(int i=0;i<productList.size();i++){
            Map<String,String> map = new HashMap<>();
            map.put("id",productList.get(i).getId().toString());
            map.put("text",productList.get(i).getCoinName());
            list.add(map);
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> findAllChannel() {

        return channelManager.findAllChannel();
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

    public Product findCoinById(Long id) {
        return productRepository.findById(id);
    }


    @Override
    public String addOrUpdate(MerchantCoinVo merchantCoinVo) {
        Long id = null;
        if(merchantCoinVo.getId()==null){
            id = new TimestampPkGenerator().next(getClass());
            MerchantCoin merchantCoin = merchantCoinDao.selectByMerchantIdAndCoinId(merchantCoinVo.getMerchantId(), merchantCoinVo.getCoinId());
            if(merchantCoin!=null){
                return "merchantCoinExist";
            }
        }

        MerchantCoin merchantCoin = createMerchantCoin(merchantCoinVo, id);
        if(merchantCoin==null){
            return "coinNotExist";
        }
        if(merchantCoinVo.getId()==null){
            int insert = merchantCoinDao.insert(merchantCoin);
            System.out.println(merchantCoin);
            if(insert>0){
                return "success";
            }else{
                return "insertFailed";
            }
        }else{
            System.out.println(merchantCoin);

            int i = merchantCoinDao.updateMerchantCoinById(merchantCoin, merchantCoin.getId().toString());
            if(i>0){
                return "success";

            }else{
                return "updateFailed";
            }
        }
    }


    public MerchantCoin createMerchantCoin(MerchantCoinVo merchantCoinVo ,Long id){
        MerchantCoin merchantCoin = new MerchantCoin();
        if(id!=null){
            merchantCoin.setId(id);
            SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            merchantCoin.setCreateUserName(securityUserDetails.getUsername());
            merchantCoin.setCreateTime(new Date());
        }else{
            merchantCoin.setId(merchantCoinVo.getId());
            merchantCoin.setCreateUserName(merchantCoinVo.getCreateUserName());
            SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            merchantCoin.setUpdateUserName(securityUserDetails.getUsername());
            merchantCoin.setCreateTime(merchantCoinVo.getCreateTime());
            merchantCoin.setUpdateTime(new Date());

        }
        long coinId = merchantCoinVo.getCoinId();
        Product coin = findCoinById(coinId);
        if(coin!=null){
            merchantCoin.setCoinId(coin.getId());
            merchantCoin.setCoinName(coin.getCoinName());
            merchantCoin.setTokenAddress(coin.getTokenAddress());
        }else{
            return null;
        }

        merchantCoin.setMerchantId(merchantCoinVo.getMerchantId());
        merchantCoin.setStatus(0);
        merchantCoin.setAddress(merchantCoinVo.getAddress());
        merchantCoin.setChannel(merchantCoinVo.getChannel());
        return merchantCoin;
    }

}
