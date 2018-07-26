package com.mbr.admin.manager.app.impl;

import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.Banner;
import com.mbr.admin.domain.app.HelpType;
import com.mbr.admin.manager.app.HelpTypeManager;
import com.mbr.admin.repository.HelpRepository;
import com.mbr.admin.repository.HelpTypeRepository;
import org.omg.CORBA.ARG_OUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HelpTypeManagerImpl implements HelpTypeManager {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private HelpTypeRepository helpTypeRepository;

    @Override
    public Map<String, Object> queryList(Long id,Pageable page) {
        Query query = new Query();
        Criteria c = new Criteria();
        if(id!=null){
            c.andOperator(Criteria.where("id").is(id));
        }
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = new Sort(orders);
        if(null!=sort){
            query.with(sort);
        }
        query.addCriteria(c);
        long count = mongoTemplate.count(query, HelpType.class);
        List<HelpType> helpTypeList = mongoTemplate.find(query.with(page), HelpType.class);
        Map<String,Object> map = new HashMap<>();
        map.put("total",count);
        map.put("list",helpTypeList);
        return map;
    }

    @Override
    public String addOrUpdate(HelpType helpType) throws MerchantException {
        System.out.println(helpType);
        if(helpType.getId()==null){
            Long id = new TimestampPkGenerator().next(getClass());
            helpType.setId(id);
            helpType.setCreateTime(new Date());
            System.out.println(helpType);
        }
        HelpType save = helpTypeRepository.save(helpType);
        if(save!=null){
            return "success";
        }else{
            throw new MerchantException("添加/更新失败");
        }
    }

    @Override
    public void deleteHelpType(Long id) {
        helpTypeRepository.delete(id);
    }

    @Override
    public Object queryById(Long id) {

        return helpTypeRepository.findOne(id);
    }
}
