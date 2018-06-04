package com.mbr.admin.manager.app.impl;


import com.mbr.admin.domain.app.EthTransaction;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.manager.app.EthTransactionManager;
import com.mbr.admin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EthTransactionManagerImpl implements EthTransactionManager {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Map<String,Object>> queryList(String orderId, String from, String to) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(orderId!=null){
            criteria.andOperator(Criteria.where("orderId").is(orderId));
        }
        if(from!=null){
            criteria.andOperator(Criteria.where("from").is(from));
        }
        if(to!=null){
            criteria.andOperator(Criteria.where("to").is(to));
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));
        query.addCriteria(criteria);
        List<EthTransaction> ethTransactionList = mongoTemplate.find(query, EthTransaction.class);

        List<Map<String,Object>> list = new ArrayList<>();

        for(int i=0;i<ethTransactionList.size();i++) {
            Map<String, Object> map = new HashMap<>();
            Product coin = productRepository.findById(Long.parseLong(ethTransactionList.get(i).getCoinId()));
            map.put("id", ethTransactionList.get(i).getTo());
            map.put("orderId", ethTransactionList.get(i).getOrderId());
            map.put("txStatus", ethTransactionList.get(i).getTxStatus());
            map.put("from", ethTransactionList.get(i).getFrom());
            map.put("to", ethTransactionList.get(i).getTo());
            String value = ethTransactionList.get(i).getValue();
            int coinDecimals = coin.getCoinDecimals();
           /* if (!value.equals("0")) {
                map.put("value", value.substring(0, value.length() - coinDecimals));
            }
            else{
                map.put("value",value);
            }*/
            map.put("value",value);
            map.put("isErc20",ethTransactionList.get(i).isErc20());
            map.put("createTime",ethTransactionList.get(i).getCreateTime());
            list.add(map);
        }

        return list;
    }
}
