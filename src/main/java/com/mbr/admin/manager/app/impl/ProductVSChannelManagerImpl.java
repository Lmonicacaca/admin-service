package com.mbr.admin.manager.app.impl;

import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.ProductVsChannel;
import com.mbr.admin.domain.app.Vo.ProductVSChannelVo;
import com.mbr.admin.manager.app.ProductManager;
import com.mbr.admin.manager.app.ProductVSChannelManager;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.repository.ProductVsChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ProductVSChannelManagerImpl implements ProductVSChannelManager {

    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private ProductManager productManager;
    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductVsChannelRepository productVsChannelRepository;
    @Override
    public List<Map<String, Object>> queryChannel() {
        List<Map<String, Object>> channelList = channelManager.findAllChannel();
        return channelList;
    }

    @Override
    public List<Map<String, Object>> queryProductId() {
        return productManager.findAllId();
    }

    @Override
    public List<Map<String, Object>> queryOnlineStatus() {

        return createLoad("可用","不可用","0","1");
    }

    @Override
    public List<Map<String, Object>> queryIsForceShow() {
        return createLoad("是","否","0","1");
    }

    @Override
    public List<Map<String, Object>> queryMerchantShow() {
        return createLoad("是","否","true","false");
    }

    @Override
    public Map<String,Object> queryList(Long channelQuery,Long productQuery,Pageable page) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(channelQuery!=null&& productQuery!=null){
            criteria.andOperator(Criteria.where("channel").is(channelQuery),Criteria.where("productId").is(productQuery));
        }else if(channelQuery!=null){
            criteria.andOperator(Criteria.where("channel").is(channelQuery));
        }else if(productQuery!=null){
            criteria.andOperator(Criteria.where("productId").is(productQuery));
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = new Sort(orders);
        if(null!=sort){
            query.with(sort);
        }
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query, ProductVsChannel.class);
        List<ProductVsChannel> list = mongoTemplate.find(query.with(page), ProductVsChannel.class);
        Map<String,Object> map = new HashMap<>();
        map.put("total",count);
        map.put("list",list);
        return map;
    }

    @Override
    public String addOrUpdate(ProductVSChannelVo productVSChannelVo) throws MerchantException {
        ProductVsChannel productVSChannel = createProductVSChannel(productVSChannelVo);
        if(productVSChannelVo.getId()==null||productVSChannelVo.getId().equals("")){
            Long id = new TimestampPkGenerator().next(getClass());
            productVSChannel.setId(id);
            //先查询是否存在相同记录
            ProductVsChannel byChannelAndProductId = productVsChannelRepository.findByChannelAndProductId(productVSChannelVo.getChannel(), productVSChannelVo.getProductId());
            if(byChannelAndProductId!=null){
                throw new MerchantException("已存在相同记录，请勿重复添加");
            }
        } else{
            //判断是否修改了渠道号和币id，修改了则需要判断是否存在相同记录
            if(!productVSChannelVo.getChannel().equals(productVSChannelVo.getOldChannel())||!productVSChannelVo.getOldProductId().equals(productVSChannelVo.getProductId())) {
                ProductVsChannel byChannelAndProductId = productVsChannelRepository.findByChannelAndProductId(productVSChannelVo.getChannel(), productVSChannelVo.getProductId());
                if (byChannelAndProductId != null) {
                    throw new MerchantException("已存在相同记录，请勿重复添加");
                }
            }
        }
        int count = productVsChannelRepository.countByChannel(productVSChannelVo.getChannel());
        productVSChannel.setOrderNo(count+1);
        ProductVsChannel save = productVsChannelRepository.save(productVSChannel);
        if(save!=null){
            return "success";
        }else{
            throw new MerchantException("添加/更新失败");
        }

    }

    @Override
    public void deleteProductVSChannel(Long id) {
        productVsChannelRepository.delete(id);
    }

    @Override
    public ProductVsChannel queryById(Long id) {
        return productVsChannelRepository.findOne(id);
    }

    public List<Map<String,Object>> createLoad(String text1,String text2,String id1,String id2){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        Map<String,Object> map2 = new HashMap<>();
        map1.put("id",id1);
        map1.put("text",text1);
        map2.put("id",id2);
        map2.put("text",text2);
        list.add(map1);
        list.add(map2);
        return list;
    }


    public ProductVsChannel createProductVSChannel(ProductVSChannelVo productVSChannelVo){
        ProductVsChannel productVsChannel = new ProductVsChannel();
        if(productVSChannelVo.getId()!=null&&!productVSChannelVo.getId().equals("")){
            productVsChannel.setId(productVSChannelVo.getId());
        }

        productVsChannel.setCreateTime(new Date());
        productVsChannel.setChannel(productVSChannelVo.getChannel());
        productVsChannel.setProductId(productVSChannelVo.getProductId());
        productVsChannel.setMerchantShow(productVSChannelVo.getMerchantShow());
        productVsChannel.setIsForceShow(productVSChannelVo.getIsForceShow());
        productVsChannel.setOnlineStatus(productVSChannelVo.getOnlineStatus());

        return productVsChannel;
    }
}
