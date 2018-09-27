package com.mbr.admin.manager.app.impl;

import com.mbr.admin.common.storage.CdnService;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.common.utils.SelectUtils;
import com.mbr.admin.domain.app.Banner;
import com.mbr.admin.domain.app.Vo.BannerVo;
import com.mbr.admin.manager.app.BannerManager;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.repository.BannerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
    @Value("${uploadCdnBannerUrl}")
    private String uploadCdnBannerUrl;
    @Value("${upload_url}")
    private String upload_url;
    @Value("${image_url}")
    private String image_url;
    @Override
    public Map<String,Object> queryAll(int i,String url, Pageable page) {
        Query query = new Query();
        Criteria c = new Criteria();
        if(url!=null){
            c.andOperator(Criteria.where("url").is(url));
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
        //兼容以往的中间服务器和阿里云服务器地址
        for(int j=0;j<bannerList.size();j++){
            String image = bannerList.get(j).getImage();
            if(image!=null&&!image.equals("")){
                if(image.startsWith("http")){

                }else {
                    bannerList.get(j).setImage(image_url+image);
                }
            }
        }
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
    public String addOrUpdate(BannerVo bannerVo, MultipartFile multipartFile) throws MerchantException {
        //上传图片
        String imgUrl = null;
        if(multipartFile!=null){
            imgUrl = uploadImage(multipartFile);
        }
        Banner banner = createBanner(bannerVo, imgUrl);
        System.out.println(bannerVo);
        System.out.println(banner);
        bannerRepository.save(banner);
        return "success";
    }

    @Override
    public Banner queryById(Long id) {
        return bannerRepository.findOne(id);
    }

    public String uploadImage(MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        String imgUrl = CdnService.genSaveKey(uploadCdnBannerUrl, String.valueOf(System.currentTimeMillis()+originalFilename));
        Optional<URL> img = cdnService.put(imgUrl, multipartFile, getFileType(multipartFile.getName()));
        String imgUrlFull = upload_url+imgUrl;
        return imgUrlFull;
    }

    public Banner createBanner(BannerVo bannerVo,String imgUrl){
        Banner banner = new Banner();
        if(bannerVo.getId()==null||bannerVo.getId().toString().equals("")){
            Long id = System.currentTimeMillis();
            banner.setId(id);

        }else{
            banner.setId(bannerVo.getId());
        }
        if(imgUrl==null){
            banner.setImage(bannerVo.getOldImage());
        }else{
            banner.setImage(imgUrl);
        }
        if(bannerVo.getType()!=null&&!bannerVo.getType().toString().equals("")){
            int count = bannerRepository.countByType(Integer.parseInt(bannerVo.getType()));
            if(bannerVo.getType().equals(bannerVo.getOldType())){
                banner.setOrderBy(Integer.parseInt(bannerVo.getOldOrderBy()));
            }else{
                banner.setOrderBy(count+1);
            }
        }else{
            banner.setOrderBy(1);
        }
        banner.setCreateTime(new Date());
        banner.setChannel(Long.parseLong(bannerVo.getChannel()));
        banner.setUrl(bannerVo.getUrl());
        banner.setType(Integer.parseInt(bannerVo.getType()));
        banner.setStatus(Integer.parseInt(bannerVo.getStatus()));

        return banner;
    }
    public String getFileType(String name) {

        if (StringUtils.isEmpty(name)) {
            return null;
        }
        int idx = name.lastIndexOf('.');
        if (idx >= 0) {
            return name.substring(idx + 1).toLowerCase();
        } else {
            return "jpg";
        }

    }
}
