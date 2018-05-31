package com.mbr.admin.manager.app.impl;

import com.mbr.admin.domain.app.Notification;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.manager.app.NotificationManager;
import com.mbr.admin.repository.ChannelRepository;
import com.mbr.admin.repository.NotificationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationManagerImpl implements NotificationManager {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Resource
    private NotificationRepository notificationRepository;
    @Resource
    private ChannelRepository channelRepository;

    @Override
    public List<Notification> queryList(int type, int transfer) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(type!=-1 && transfer==-1){
            criteria.andOperator(Criteria.where("type").is(type));
        }else if(transfer!=-1 && type==-1){
            criteria.andOperator(Criteria.where("transfer").is(transfer));
        }else if(transfer!=-1 && type!=-1){
            criteria.andOperator(Criteria.where("transfer").is(transfer),Criteria.where("type").is(type));
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));
        query.addCriteria(criteria);
        return mongoTemplate.find(query,Notification.class);
    }

    @Override
    public Notification queryById(Long id) {
        Notification notification = notificationRepository.findOne(id);
        return notification;
    }

    @Override
    public void updateById(Long id,String title) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("id").is(id));
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("title",title);
        mongoTemplate.upsert(query,update,Notification.class);
    }

    @Override
    public List<Map<String, Object>> queryChannel() {
        List<Channel> channelList = channelRepository.findAllByStatus(0);
        List<Map<String,Object>> list = new ArrayList<>();
        for(int j=0;j<channelList.size();j++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",channelList.get(j).getId());
            map.put("text",channelList.get(j).getId());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryType() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id","0");
        map.put("text","交易");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id","1");
        map1.put("text","支付");
        Map<String,Object> map2 = new HashMap<>();
        map2.put("id","2");
        map2.put("text","公告");
        list.add(map);
        list.add(map1);
        list.add(map2);
        return list;
    }

    @Override
    public List<Map<String, Object>> queryTransfer() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id","0");
        map.put("text","转入");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id","1");
        map1.put("text","转出");
        Map<String,Object> map2 = new HashMap<>();
        map2.put("id","2");
        map2.put("text","提现");
        Map<String,Object> map3 = new HashMap<>();
        map3.put("id","3");
        map3.put("text","支付");
        list.add(map);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        return list;
    }

    @Override
    public void insertNotification(Notification notification) {
        notificationRepository.insert(notification);
    }

}
