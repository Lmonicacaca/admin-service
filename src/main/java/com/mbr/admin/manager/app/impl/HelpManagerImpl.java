package com.mbr.admin.manager.app.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.Help;
import com.mbr.admin.manager.app.HelpManager;
import com.mbr.admin.repository.HelpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HelpManagerImpl implements HelpManager {
    @Autowired
    private HelpRepository helpRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Help> queryList(String title) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(title!=null&&title!=""){
            criteria.andOperator(Criteria.where("title").regex(title));
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));
        query.addCriteria(criteria);
        return mongoTemplate.find(query,Help.class);
    }

    @Override
    public int addOrUpdate(Help help) {
        if(help.getId()==null){
            Long id = new TimestampPkGenerator().next(getClass());
            help.setId(id);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date());
            help.setCreateTime(today);
            Help save = helpRepository.save(help);
            if(save!=null){
                return 1;
            }

        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String today = simpleDateFormat.format(new Date());
            help.setCreateTime(today);
            Help save = helpRepository.save(help);
            if(save!=null){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void deleteHelp(Long id) {
        helpRepository.delete(id);
    }

    @Override
    public Help queryById(Long id) {
        return helpRepository.findOne(id);
    }
}
