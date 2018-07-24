package com.mbr.admin.manager.app.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.Help;
import com.mbr.admin.domain.app.HelpType;
import com.mbr.admin.manager.app.HelpManager;
import com.mbr.admin.repository.HelpRepository;
import com.mbr.admin.repository.HelpTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HelpManagerImpl implements HelpManager {
    @Autowired
    private HelpRepository helpRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private HelpTypeRepository helpTypeRepository;
    @Override
    public Map<String,Object> queryList(String title, Pageable page) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(title!=null&&title!=""){
            criteria.andOperator(Criteria.where("title").regex(title));
        }
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query, Help.class);
        if(count>page.getPageSize()){
            query.with(page);

        }
        List<Help> list = mongoTemplate.find(query, Help.class);
        Map<String,Object> map = new HashMap<>();
        map.put("total",count);
        map.put("list",list);
        return map;
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

    @Override
    public List<Map<String, Object>> queryType() {
        List<HelpType> helpTypeList = helpTypeRepository.findAll();
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<helpTypeList.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",helpTypeList.get(i).getId());
            map.put("text",helpTypeList.get(i).getName());
            list.add(map);
        }
        return list;
    }
}
