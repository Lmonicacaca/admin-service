package com.mbr.admin.manager.app.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.ProductApply;
import com.mbr.admin.domain.app.ProductVsChannel;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.manager.app.AuditProductManager;
import com.mbr.admin.repository.ProductApplyRepository;
import com.mbr.admin.repository.ProductRepository;
import com.mbr.admin.repository.ProductVsChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuditProductManagerImpl implements AuditProductManager {

    @Autowired
    private ProductApplyRepository productApplyRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVsChannelRepository productVsChannelRepository;
    @Override
    public Map<String,Object> queryList(String channelSearch, Pageable page) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(channelSearch!=null&&channelSearch!=""){
            criteria.andOperator(Criteria.where("channel").is(channelSearch));
        }
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = new Sort(orders);
        if(null!=sort){
            query.with(sort);
        }
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query.with(page), ProductApply.class);
        List<ProductApply> list = mongoTemplate.find(query, ProductApply.class);
        Map<String,Object> map = new HashMap<>();
        map.put("total",count);
        map.put("list",list);
        return map;
    }

    @Override
    public ProductApply queryById(String id) {
        return productApplyRepository.findOne(Long.parseLong(id));
    }

    @Override
    public Object auditProductPass(ProductApply productApply){
        //修改审核表的状态
        productApply.setStatus(1);
        productApply.setUpdateTime(new Date());
        productApplyRepository.save(productApply);
        //添加币数据到product
        String productId = "";
        Product productSerach = productRepository.findByCoinNameAndTokenAddress(productApply.getCoinName(), productApply.getTokenAddress());
        if(productSerach==null){
            productId =  insertProduct(productApply);
        }else{
            productId = productSerach.getId().toString();
        }
        if(productVsChannelRepository.findByChannelAndProductId(productApply.getChannel(),productId)==null){
            //添加币种vs渠道到对应表中
            insertProductVsChannel(productApply,productId);
        }

        return "success";
    }

    @Override
    public Object auditProductFailed(String id) {
        ProductApply productApply = productApplyRepository.findOne(Long.parseLong(id));
        productApply.setStatus(2);
        productApply.setUpdateTime(new Date());
        ProductApply save = productApplyRepository.save(productApply);
        return save;

    }


    //添加币种信息
    public String insertProduct(ProductApply productApply){
        Product product = new Product();
        String  productId = "";
        productId = new TimestampPkGenerator().next(getClass()).toString();
        product.setId(Long.parseLong(productId));
        product.setCreateTime(new Date());
        product.setCoinName(productApply.getCoinName());
        product.setCoinType(1);
        product.setCoinDescription(productApply.getCoinDescription());
        product.setOnlineStatus(0);
        product.setChainType("ERC20");
        product.setTokenAddress(productApply.getTokenAddress());
        product.setCoinDecimals(productApply.getCoinDecimals());
        int count = (int)productApplyRepository.count()+1;
        product.setOrderNo(count);
        product.setIsForceShow(0);
        product.setCoinErc20(1);
        product.setMerchantShow(true);
        product.setGasLimit("55000");
        Product save1 = productRepository.save(product);
        return productId;
    }

    //添加币种vs渠道表信息
    public void insertProductVsChannel(ProductApply productApply,String productId){
        ProductVsChannel productVsChannel = new ProductVsChannel();
        String pvcId = new TimestampPkGenerator().next(getClass()).toString();
        productVsChannel.setId(pvcId);
        productVsChannel.setChannel(productApply.getChannel());
        productVsChannel.setProductId(productId);
        productVsChannel.setOnlineStatus(0);
        productVsChannel.setOrderNo((int)productVsChannelRepository.count()+1);
        productVsChannel.setIsForceShow(0);
        productVsChannel.setMerchantShow(true);
        productVsChannel.setCreateTime(new Date());
        ProductVsChannel save2 = productVsChannelRepository.save(productVsChannel);
    }
}
