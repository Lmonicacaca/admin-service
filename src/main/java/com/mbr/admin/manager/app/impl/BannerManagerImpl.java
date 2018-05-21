package com.mbr.admin.manager.app.impl;

import com.mbr.admin.domain.app.Banner;
import com.mbr.admin.manager.app.BannerManager;
import com.mbr.admin.repository.BannerRepository;
import org.apache.commons.lang3.StringUtils;
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

@Service("bannerManager")
public class BannerManagerImpl implements BannerManager {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Banner> queryAll(int i,String url) {
        Query query = new Query();
        Criteria c = new Criteria();
        if(StringUtils.isNotEmpty(url)){
            c.andOperator(Criteria.where("status").is(i),Criteria.where("url").is(url));
        }else{
            c.andOperator(Criteria.where("status").is(i));
        }

        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));
        query.addCriteria(c);
        return mongoTemplate.find(query,Banner.class);
    }

    @Override
    public void deleteBanner(Long id) {
        bannerRepository.delete(id);
    }


    @Override
    public Banner queryById(Long id) {
        return bannerRepository.queryById(id);
    }

    @Override
    public int countAll() {
        return (int)bannerRepository.count();
    }

    @Override
    public Banner saveOrUpdate(Banner banner) {
        return bannerRepository.save(banner);
    }
}
