package com.mbr.admin.manager.app.impl;

import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.manager.app.PrivacyPolicyAndAboutManager;
import com.mbr.admin.repository.ChannelRepository;
import com.mbr.admin.repository.PrivacyPolicyAndAboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivacyPolicyAndAboutManagerImpl implements PrivacyPolicyAndAboutManager {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private PrivacyPolicyAndAboutRepository privacyPolicyAndAboutRepository;
    @Override
    public List<PrivacyPolicyAndAbout> queryList(String channel) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(channel!=null){
            criteria.andOperator(Criteria.where("channel").is(channel));
        }

        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));
        query.addCriteria(criteria);
        return mongoTemplate.find(query,PrivacyPolicyAndAbout.class);
    }

    @Override
    public List<Channel> queryChannel() {
        return channelRepository.findAllByStatus(0);
    }

    @Override
    public void deletePrivacyPolicyAndAbout(Long id) {
        channelRepository.delete(id);
    }

    @Override
    public PrivacyPolicyAndAbout queryById(Long id) {
        return privacyPolicyAndAboutRepository.findOne(id);
    }

    @Override
    public void updateById(PrivacyPolicyAndAbout privacyPolicyAndAbout) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("id").is(privacyPolicyAndAbout.getId()));
        Update update = new Update();
        update.set("content",privacyPolicyAndAbout.getContent());
        query.addCriteria(criteria);
        mongoTemplate.upsert(query,update,PrivacyPolicyAndAbout.class);
    }
}
