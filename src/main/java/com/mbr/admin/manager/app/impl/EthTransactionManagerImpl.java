package com.mbr.admin.manager.app.impl;


import com.mbr.admin.domain.app.EthTransaction;
import com.mbr.admin.domain.app.Vo.EthTransactionVo;
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
    public List<EthTransactionVo> queryList(String orderId, String from, String to) {
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

        List<EthTransactionVo> list = new ArrayList<>();
        for(int i=0;i<ethTransactionList.size();i++) {

            EthTransactionVo ethTransactionVo = new EthTransactionVo();
            ethTransactionVo.setId(ethTransactionList.get(i).getId());
            ethTransactionVo.setOrderId(ethTransactionList.get(i).getOrderId());
            ethTransactionVo.setTxStatus(ethTransactionList.get(i).getTxStatus());
            ethTransactionVo.setCreateTime(ethTransactionList.get(i).getCreateTime());
            ethTransactionVo.setFrom(ethTransactionList.get(i).getFrom());
            ethTransactionVo.setTo(ethTransactionList.get(i).getTo());
            ethTransactionVo.setValue(ethTransactionList.get(i).getValue());
            ethTransactionVo.setErc20(ethTransactionList.get(i).isErc20());
            if(ethTransactionList.get(i).getCoinId()!=null&&ethTransactionList.get(i).getCoinId()!=0){
                Product coin = productRepository.findById(ethTransactionList.get(i).getCoinId());
                ethTransactionVo.setCoinName(coin.getCoinName());
            }
            list.add(ethTransactionVo);
        }
        return list;
    }
}
