package com.mbr.admin.manager.app.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.manager.app.PrivacyPolicyAndAboutManager;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.repository.ChannelRepository;
import com.mbr.admin.repository.PrivacyPolicyAndAboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrivacyPolicyAndAboutManagerImpl implements PrivacyPolicyAndAboutManager {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private PrivacyPolicyAndAboutRepository privacyPolicyAndAboutRepository;

    @Override
    public Map<String,Object> queryList(String channel, Pageable page) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(channel!=null){
            criteria.andOperator(Criteria.where("channel").is(channel));
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = new Sort(orders);
        if(null!=sort){
            query.with(sort);
        }
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query, PrivacyPolicyAndAbout.class);
        List<PrivacyPolicyAndAbout> list = mongoTemplate.find(query.with(page), PrivacyPolicyAndAbout.class);
        Map<String,Object> map = new HashMap<>();
        map.put("total",count);
        map.put("list",list);
        return map;
    }

    @Override
    public Object queryChannel() {

        return channelManager.findAllChannel();
    }

    @Override
    public Object queryType() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id","0");
        map1.put("text","隐私协议");
        Map<String,Object> map2 = new HashMap<>();
        map2.put("id","1");
        map2.put("text","关于我们");
        list.add(map1);
        list.add(map2);
        return list;
    }

    @Override
    public int addOrUpdate(PrivacyPolicyAndAbout privacyPolicyAndAbout) {
        if(privacyPolicyAndAbout.getId()==null){
            Long id = new TimestampPkGenerator().next(getClass());
            privacyPolicyAndAbout.setId(id);
            privacyPolicyAndAbout.setCreateTime(new Date());
            PrivacyPolicyAndAbout save = privacyPolicyAndAboutRepository.save(privacyPolicyAndAbout);
            if(save!=null){
                return 1;
            }
        }else{
            if(privacyPolicyAndAbout.getContent()==null){
                return 0;
            }
            privacyPolicyAndAbout.setCreateTime(new Date());
            PrivacyPolicyAndAbout save = privacyPolicyAndAboutRepository.save(privacyPolicyAndAbout);
            if(save!=null){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void deletePrivacyPolicyAndAbout(Long id) {
        privacyPolicyAndAboutRepository.delete(id);
    }

    @Override
    public PrivacyPolicyAndAbout queryById(Long id) {
        return privacyPolicyAndAboutRepository.findOne(id);
    }

    @Override
    public String queryContent(Long id) {
        PrivacyPolicyAndAbout policyAndAbout = privacyPolicyAndAboutRepository.findById(id);
        return policyAndAbout.getContent();
    }


}
