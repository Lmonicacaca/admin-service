package com.mbr.admin.manager.app.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mbr.admin.common.storage.CdnService;
import com.mbr.admin.common.utils.FileUpload;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.AppUpdate;
import com.mbr.admin.domain.app.Vo.AppUpdateVo;
import com.mbr.admin.manager.app.AppUpdateManager;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.repository.AppUpdateRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.Length;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URL;
import java.util.*;

@Service
public class AppUpdateManagerImpl implements AppUpdateManager {
    Logger logger = LoggerFactory.getLogger(AppUpdateManagerImpl.class);
    @Value("${upload_url}")
    private String upload_url;
    @Value("${disUrl}")
    private String disUrl;
    @Value("${uploadCdnUrl}")
    private String uploadCdnUrl;
    @Value("${uploadCdnLogoUrl}")
    private String uploadCdnLogoUrl;
    @Resource
    private CdnService cdnService;
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
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = new Sort(orders);
        if(null!=sort){
            query.with(sort);
        }
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query, AppUpdate.class);

        List<AppUpdate> appUpdateList = mongoTemplate.find(query.with(page), AppUpdate.class);

        for(int i=0;i<appUpdateList.size();i++){
            String url = appUpdateList.get(i).getUrl();
            if(url!=null&&!url.equals("")){
                if(!url.startsWith("http")){
                    appUpdateList.get(i).setUrl(image_url+appUpdateList.get(i).getUrl());
                }else{
                    appUpdateList.get(i).setUrl(appUpdateList.get(i).getUrl());
                }
            }
            String plistUrl = appUpdateList.get(i).getPlistUrl();
            if(plistUrl!=null&&!plistUrl.equals("")){
                if(!plistUrl.startsWith("http")){
                    appUpdateList.get(i).setPlistUrl(image_url+appUpdateList.get(i).getPlistUrl());

                }else {
                    appUpdateList.get(i).setPlistUrl(appUpdateList.get(i).getPlistUrl());

                }
            }
            String iosLogo = appUpdateList.get(i).getIosLogo();
            if(iosLogo!=null&&!iosLogo.equals("")){
                if(iosLogo.startsWith("http")){
                    appUpdateList.get(i).setIosLogo(appUpdateList.get(i).getIosLogo());
                }else{
                    appUpdateList.get(i).setIosLogo(image_url+appUpdateList.get(i).getIosLogo());

                }
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
    public AppUpdate queryById(Long id) {
        return appUpdateRepository.findOne(id);
    }

    @Override
    public List<Map<String, Object>> queryType() {
        Object[] ids = new Object[]{"IOS","Android"};
        Object[] texts = new Object[]{"IOS","Android"};
        return createListMap(ids,texts);
    }

    @Override
    public List<Map<String, Object>> queryForce() {
        Object[] ids = new Object[]{"false","true"};
        Object[] texts = new Object[]{"false","true"};
        return createListMap(ids,texts);
    }

    @Override
    public List<Map<String, Object>> queryBuild() {
        Object[] ids = new Object[]{"release","beta"};
        Object[] texts = new Object[]{"release","beta"};
        return createListMap(ids,texts);
    }

    @Override
    public String addAppUpdate(AppUpdateVo appUpdateVo,MultipartFile[] files) throws Exception {
        if(appUpdateVo.getId()==null||appUpdateVo.getId().equals("")){
            long size = files[0].getSize();//文件大小
            Long channel = Long.parseLong(appUpdateVo.getChannel());
            String systemType = appUpdateVo.getAppUpdateType();
            String build = appUpdateVo.getAppUpdateBuild();
            String version = appUpdateVo.getVersion();
            //查询是否存在相同渠道号，相同系统名，相同build和相同版本号的包
            AppUpdate byChannelAndAppUpdateTypeAndAppUpdateBuildAndAndVersion = appUpdateRepository.findByChannelAndAppUpdateTypeAndAppUpdateBuildAndAndVersion(channel, systemType, build, version);
            if(byChannelAndAppUpdateTypeAndAppUpdateBuildAndAndVersion!=null){
                throw new MerchantException("已存在相同版本，请重新上传！");
            }
            String logoUrl = null;
            Map<String,String> map = null;
            //上传logo
            logoUrl = uploadLogoToCdn(files[1],systemType,build,version);
            //上传包
            map = uploadUrlToCdn(systemType, build, version, logoUrl, files[0]);
            String logoUrlFull = null;
            if(logoUrl!=null){
                logoUrlFull = upload_url+logoUrl;
            }
            String plistUrlFull = upload_url+map.get("plistUrl");
            String urlFull = upload_url+map.get("url");
            logger.info("logoUrlFull:"+logoUrlFull);
            logger.info("urlFull:"+urlFull);
            AppUpdate appUpdate = createAppUpdate(appUpdateVo, urlFull, logoUrlFull,plistUrlFull,size);
            logger.info(JSONObject.toJSONString(appUpdate));
            AppUpdate insert = appUpdateRepository.insert(appUpdate);
            if(insert!=null){
                return "success";
            }
        }else{
            System.out.println(appUpdateVo);
            AppUpdate appUpdate = createAppUpdate(appUpdateVo, null, null,null,0);
            System.out.println(appUpdate);
            AppUpdate save = appUpdateRepository.save(appUpdate);
            if(save!=null){
                return "success";
            }
        }

        return null;
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

    /**
     *生成下拉列表
     * @param ids
     * @param texts
     * @return
     */
   public List<Map<String,Object>> createListMap(Object[] ids,Object[] texts){
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",ids[i]);
            map.put("text",texts[i]);
            list.add(map);
        }
        return list;
   }

    /**
     * 上传logo到阿里云
     * @param file
     * @return
     */
   public String uploadLogoToCdn(MultipartFile file,String systemType,String build,String version){
       String fileName = file.getOriginalFilename();
       String newFileName = systemType+"-"+build+"-"+version+"-"+fileName;
       //上传logo到阿里云
       String logoUrl = CdnService.genSaveKey(uploadCdnLogoUrl, newFileName);
       Optional<URL> cdn_plist = cdnService.put(logoUrl, file, getFileType(file.getName()));
       logger.info("上传图片地址："+logoUrl);
       return logoUrl;
   }

    /**
     * 上传包到阿里云
     * @param systemType
     * @param build
     * @param version
     * @param imgUrl
     * @param file
     * @return
     * @throws Exception
     */
   public Map<String,String> uploadUrlToCdn(String systemType,String build,String version,String imgUrl,MultipartFile file) throws Exception {
       String fileName = file.getOriginalFilename();
       Map<String,String> map = new HashMap<>();
       //如果是ios则需要使用freemaker生成plist文件
       if(fileName.endsWith(".ipa")){
           createPlist(systemType,build,version,imgUrl);
           String plistFileName = systemType+"-"+build+"-"+version;
           File plistFile = new File(disUrl + plistFileName+".plist");
           FileInputStream inputStream = new FileInputStream(plistFile);
           String plistUrl = CdnService.genSaveKey(uploadCdnUrl, plistFileName+".plist");
           Optional<URL> cdn_plist = cdnService.put(plistUrl, inputStream, getFileType(file.getName()));
           logger.info("plistUrl:"+plistUrl);
           String ipaUrl = CdnService.genSaveKey(uploadCdnUrl, plistFileName+".ipa");
           Optional<URL> cdn_ipa = cdnService.put(ipaUrl, file, getFileType(file.getName()));
           logger.info("ipaUrl:"+ipaUrl);
           map.put("url",ipaUrl);
           map.put("plistUrl",plistUrl);
           return map;
       }

       String apkFileName = systemType+"-"+build+"-"+version+".apk";
       String apkUrl = CdnService.genSaveKey(uploadCdnUrl, apkFileName);
       Optional<URL> cdn_plist = cdnService.put(apkUrl, file, getFileType(file.getName()));
       logger.info("apkUrl"+apkUrl);
       map.put("url",apkUrl);
       return map;
   }

    /**
     * 使用free marker生成plist文件
     *
     * @throws Exception
     */
    public void createPlist(String systemType,String build,String version,String imgUrl) throws Exception {
        //创建fm的配置
        Configuration config = new Configuration();
        //指定默认编码格式
        config.setDefaultEncoding("UTF-8");
        //设置模版文件的路径
        config.setClassForTemplateLoading(AppUpdateManagerImpl.class, "/static/ftl");
        //获得模版包
        Template template = config.getTemplate("ipa.plist");//从参数文件中获取指定输出路径 ,路径示例：C:/Workspace/shop-test/src/main/webapp/html

        //将plist文件写到磁盘中
        String path_mkdirs = disUrl;
        String fileName = systemType+"-"+build+"-"+version;
        String path_file = disUrl +fileName+".plist";
        File file_mkdirs = new File(path_mkdirs);
        if (!file_mkdirs.exists()) {
            file_mkdirs.mkdirs();
        }
        File file_file = new File(path_file);
        if (!file_file.exists()) {
            file_file.createNewFile();
        }
        //定义输出流，注意必须指定编码
        FileWriter out = new FileWriter(file_file);
        Map<String, String> map = new HashMap<>();
        map.put("version", version);
        map.put("ipaDownLoadUrl", uploadCdnUrl+fileName+".ipa");
        map.put("ipaImageUrl",imgUrl);
        logger.info(JSONObject.toJSONString(map));
        //生成模版
        template.process(map, out);

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

    /**
     * 创建更新对象
     * @param appUpdateVo
     * @param url
     * @param logoUrl
     * @return
     */
    public AppUpdate createAppUpdate(AppUpdateVo appUpdateVo,String url,String logoUrl,String plistUrl,long size){
        AppUpdate appUpdate = new AppUpdate();
        String systemType = appUpdateVo.getAppUpdateType();

        if(appUpdateVo.getId()==null||appUpdateVo.getId().equals("")){
            appUpdate.setId(System.currentTimeMillis());
            appUpdate.setCreateTime(new Date());
            appUpdate.setSize(size);
            appUpdate.setIosLogo(logoUrl);
            if(systemType.equals("IOS")){
                appUpdate.setUrl(url);
                appUpdate.setPlistUrl(plistUrl);

            }else{
                appUpdate.setUrl(url);
            }
        }else {
            appUpdate.setId(appUpdateVo.getId());
            appUpdate.setCreateTime(new Date(appUpdateVo.getCreateTime()));
            appUpdate.setUpdateTime(new Date());
            appUpdate.setUrl(appUpdateVo.getOldUrl());
            appUpdate.setIosLogo(appUpdateVo.getOldIosLogo());
            appUpdate.setPlistUrl(appUpdateVo.getOldPlistUrl());
            appUpdate.setSize(appUpdateVo.getOldSize());
        }

        String version = appUpdateVo.getVersion();
        appUpdate.setVersion(version);
        appUpdate.setForce(appUpdateVo.getForce());
        appUpdate.setContent(appUpdateVo.getContent());
        appUpdate.setAppUpdateBuild(appUpdateVo.getAppUpdateBuild());
        appUpdate.setAppUpdateType(systemType);
        appUpdate.setChannel(Long.parseLong(appUpdateVo.getChannel()));
        return appUpdate;
    }

}
