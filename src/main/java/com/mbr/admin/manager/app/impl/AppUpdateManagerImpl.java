package com.mbr.admin.manager.app.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mbr.admin.common.utils.FileUpload;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.AppUpdate;
import com.mbr.admin.domain.app.Vo.AppUpdateVo;
import com.mbr.admin.manager.app.AppUpdateManager;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.repository.AppUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppUpdateManagerImpl implements AppUpdateManager {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private AppUpdateRepository appUpdateRepository;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private FileUpload fileUpload;
    @Override
    public Map<String,Object> queryList(String version,String image_url, Pageable page) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(version!=null&&version!=""){
            criteria.andOperator(Criteria.where("version").is(version));
        }
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query, AppUpdate.class);
        if(count>page.getPageSize()){
            query.with(page);
        }
        List<AppUpdate> appUpdateList = mongoTemplate.find(query, AppUpdate.class);

        for(int i=0;i<appUpdateList.size();i++){
            if(appUpdateList.get(i).getUrl()!=null&&appUpdateList.get(i).getUrl()!=""){
                appUpdateList.get(i).setUrl(image_url+appUpdateList.get(i).getUrl());
            }
            if(appUpdateList.get(i).getPlistUrl()!=null&&appUpdateList.get(i).getPlistUrl()!=""){
                appUpdateList.get(i).setPlistUrl(image_url+appUpdateList.get(i).getPlistUrl());
            }
            if(appUpdateList.get(i).getIosLogo()!=null&&appUpdateList.get(i).getIosLogo()!=""){
                appUpdateList.get(i).setIosLogo(image_url+appUpdateList.get(i).getIosLogo());
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",count);
        map.put("list",appUpdateList);
        return map;
    }

    @Override
    public void deleteAppUpdate(Long id) {
        appUpdateRepository.delete(id);
    }

    @Override
    public List<Map<String, Object>> queryChannel() {
        return channelManager.findAllChannel();
    }

    @Override
    public int addOrUpdate(AppUpdateVo appUpdateVo, HttpServletRequest request,MultipartFile[] multipartFiles) {
        String iosUrlPath = "";
        String androidUrlPath = "";
        String plistUrlPath = "";
        String logoPath = "";
        Map<String, MultipartFile> iosUrlMap = null;
        Map<String, MultipartFile> androidUrlMap = null;
        Map<String, MultipartFile> plistMap = null;
        Map<String, MultipartFile> imgMap = null;
        for(int i=0;i<multipartFiles.length;i++){
            if(multipartFiles[i].getOriginalFilename().endsWith(".ipa")){
                iosUrlMap = new HashMap<>();
                iosUrlMap.put("file",multipartFiles[i]);
            }else if(multipartFiles[i].getOriginalFilename().endsWith(".apk")){
                androidUrlMap = new HashMap<>();
                androidUrlMap.put("file",multipartFiles[i]);
            }
            else if(multipartFiles[i].getOriginalFilename().endsWith(".plist")){
                plistMap = new HashMap<>();
                plistMap.put("file",multipartFiles[i]);
            }
            else{
                imgMap = new HashMap<>();
                imgMap.put("file",multipartFiles[i]);
            }

        }

        if(iosUrlMap!=null){

            iosUrlPath = getSavePath(iosUrlMap);
        }else{
            iosUrlPath = appUpdateVo.getOldUrl();
        }
        if(androidUrlMap!=null){

            androidUrlPath = getSavePath(androidUrlMap);
        }else{
            androidUrlPath = appUpdateVo.getOldUrl();
        }
        if(plistMap!=null){

            plistUrlPath = getSavePath(plistMap);
        }else{
            plistUrlPath = appUpdateVo.getOldPlistUrl();
        }
        if(imgMap!=null){

            logoPath = getSavePath(imgMap);
        }else{
            logoPath = appUpdateVo.getOldImg();
        }
        AppUpdate appUpdate = createAppUpdate(appUpdateVo, iosUrlPath, androidUrlPath, plistUrlPath, logoPath);
        AppUpdate save = appUpdateRepository.save(appUpdate);
        if(save!=null){
            return 1;
        }
        return 0;
    }

    @Override
    public AppUpdate queryById(Long id) {
        return appUpdateRepository.findOne(id);
    }

    @Override
    public List<Map<String, Object>> queryType() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id","IOS");
        map1.put("text","IOS");
        Map<String,Object> map2 = new HashMap<>();
        map2.put("id","Android");
        map2.put("text","Android");
        list.add(map1);
        list.add(map2);
        return list;
    }

    @Override
    public List<Map<String, Object>> queryForce() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id","false");
        map1.put("text","false");
        Map<String,Object> map2 = new HashMap<>();
        map2.put("id","true");
        map2.put("text","true");
        list.add(map1);
        list.add(map2);
        return list;
    }

    public String getSavePath(Map<String, MultipartFile> mapFiles){
       String json = fileUpload.httpClientUploadFile(mapFiles);
       Map map = JSONObject.toJavaObject(JSON.parseObject(json), Map.class);
       String result = "";
       if((Integer)map.get("code")==200){
           Map<String,String> mapRequest = (Map<String,String>)map.get("data");
           for (Map.Entry<String,String> entry:mapRequest.entrySet()) {
               result = entry.getValue();
           }
       }
        return result;
   }

   public AppUpdate createAppUpdate(AppUpdateVo appUpdateVo,String iosUrlPath,String androidUrlPath,String plistUrlPath,String logoPath){
       AppUpdate appUpdate = new AppUpdate();
       if(appUpdateVo.getId()==null){
           Long id = new TimestampPkGenerator().next(getClass());
           appUpdate.setId(id);
       }else{
           appUpdate.setId(appUpdateVo.getId());
       }
       appUpdate.setContent(appUpdateVo.getContent());
       appUpdate.setCreateTime(appUpdateVo.getCreateTime());
       appUpdate.setVersion(appUpdateVo.getVersion());
       appUpdate.setForce(appUpdateVo.getForce());
       appUpdate.setAppUpdateType(appUpdateVo.getAppUpdateType());
       appUpdate.setChannel(Long.parseLong(appUpdateVo.getChannel()));
       if(appUpdateVo.getAppUpdateType().equals("IOS")){
           appUpdate.setUrl(iosUrlPath);
           appUpdate.setPlistUrl(plistUrlPath);
           appUpdate.setIosLogo(logoPath);
       }else{
           appUpdate.setUrl(androidUrlPath);
       }
       return appUpdate;
   }
}
