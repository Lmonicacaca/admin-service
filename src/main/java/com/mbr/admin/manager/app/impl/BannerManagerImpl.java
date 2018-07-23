package com.mbr.admin.manager.app.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mbr.admin.common.utils.DateUtil;
import com.mbr.admin.common.utils.FileUpload;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.Banner;
import com.mbr.admin.domain.app.Vo.BannerVo;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.manager.app.BannerManager;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.repository.BannerRepository;
import com.mbr.admin.repository.ChannelRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("bannerManager")
public class BannerManagerImpl implements BannerManager {

    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private ChannelManager channelManager;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private FileUpload fileUpload;

    @Override
    public Map<String,Object> queryAll(int i,String url, Pageable page) {
        Query query = new Query();
        Criteria c = new Criteria();
        if(url!=null){
            c.andOperator(Criteria.where("status").is(i),Criteria.where("url").is(url));
        }else{
            c.andOperator(Criteria.where("status").is(i));
        }

        query.addCriteria(c);
        Long count = mongoTemplate.count(query, Banner.class);
        if(count>page.getPageSize()){
            query.with(page);
        }
        List<Banner> bannerList = mongoTemplate.find(query, Banner.class);
        Map<String,Object> map = new HashMap<>();
        map.put("total",count);
        map.put("list",bannerList);
         return map;
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
    public Banner saveOrUpdate(HttpServletRequest request, BannerVo bannerVo) {
        Banner banner = new Banner();
        Long id = bannerVo.getId();
        if(id == null){
            id=new TimestampPkGenerator().next(getClass());
            banner.setId(id);
            int count = countAll();
            int orderBy = banner.getOrderBy();
            if(orderBy == 0){
                orderBy = count%3==0?1:count%3;
            }
            banner.setOrderBy(orderBy);
            banner.setCreateTime(DateUtil.formatDateTime(new Date()));
            banner.setStatus(0);
            banner.setUrl(bannerVo.getUrl());
            banner.setType(bannerVo.getType());
            banner.setChannel(Long.parseLong(bannerVo.getChannel()));
        }else{
            banner.setId(bannerVo.getId());
            banner.setOrderBy(bannerVo.getOrderBy());
            banner.setUrl(bannerVo.getUrl());
            banner.setType(bannerVo.getType());
            banner.setChannel(Long.parseLong(bannerVo.getChannel()));
            banner.setCreateTime(DateUtil.formatDateTime(new Date()));
            banner.setStatus(0);
        }
        Map<String, MultipartFile> mapFiles = fileUpload.getFile(request);
        if(mapFiles.size()!=0){
            String json = fileUpload.httpClientUploadFile(mapFiles);
            Map map = JSONObject.toJavaObject(JSON.parseObject(json), Map.class);
            if((Integer)map.get("code")==200){
                Map<String,String> mapRequest = (Map<String,String>)map.get("data");
                for (Map.Entry<String,String> entry:mapRequest.entrySet()) {
                    banner.setImage(entry.getValue());
                }
            }
        }
        else {
            banner.setImage(bannerVo.getSimage());
        }

        return bannerRepository.save(banner);
    }

    @Override
    public List<Map<String, Object>> queryChannel() {

        return channelManager.findAllChannel();
    }

    @Override
    public List<Map<String, Object>> queryBannerType() {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        List<String> listText = new ArrayList<>();
        listText.add("余额");
        listText.add("商家");
        listText.add("支付");
        for(int i=1;i<=3;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",i);
            map.put("text",listText.get(i-1));
            list.add(map);
        }
        return list;
    }
}
