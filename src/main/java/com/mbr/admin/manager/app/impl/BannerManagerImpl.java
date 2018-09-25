package com.mbr.admin.manager.app.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mbr.admin.common.storage.CdnService;
import com.mbr.admin.common.utils.DateUtil;
import com.mbr.admin.common.utils.FileUpload;
import com.mbr.admin.common.utils.SelectUtils;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.*;

@Service("bannerManager")
public class BannerManagerImpl implements BannerManager {

    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private ChannelManager channelManager;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Resource
    private CdnService cdnService;


    @Override
    public Map<String,Object> queryAll(int i,String url, Pageable page) {
        Query query = new Query();
        Criteria c = new Criteria();
        if(url!=null){
            c.andOperator(Criteria.where("status").is(i),Criteria.where("url").is(url));
        }else{
            c.andOperator(Criteria.where("status").is(i));
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = new Sort(orders);
        if(null!=sort){
            query.with(sort);
        }
        query.addCriteria(c);
        long count = mongoTemplate.count(query, Banner.class);
        List<Banner> bannerList = mongoTemplate.find(query.with(page), Banner.class);
        Map<String,Object> map = new HashMap<>();
        map.put("total",count);
        map.put("list",bannerList);
         return map;
    }

    @Override
    public void deleteById(Long id) {
        bannerRepository.delete(id);
    }

    @Override
    public List<Map<String, Object>> queryChannel() {

        return channelManager.findAllChannel();
    }

    @Override
    public List<Map<String, Object>> queryBannerType() {
        Object[] ids = new Object[]{"1","2","3"};
        Object[] texts = new Object[]{"余额","商家","支付"};
        return SelectUtils.createListMap(ids,texts);
    }

    @Override
    public List<Map<String, Object>> queryStatus() {
        Object[] ids = new Object[]{"0","1"};
        Object[] texts = new Object[]{"可用","不可用"};
        return SelectUtils.createListMap(ids,texts);
    }

    @Override
    public String addOrUpdate(BannerVo bannerVo, MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();

//        String plistUrl = CdnService.genSaveKey(uploadCdnUrl, plistFileName+".plist");
//        Optional<URL> cdn_plist = cdnService.put(plistUrl, inputStream, getFileType(file.getName()));
        return null;
    }

    public Banner createBanner(BannerVo bannerVo){
        Banner banner = new Banner();
        return banner;
    }

}
