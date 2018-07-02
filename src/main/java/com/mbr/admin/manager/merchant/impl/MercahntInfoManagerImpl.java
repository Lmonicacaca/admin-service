package com.mbr.admin.manager.merchant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mbr.admin.common.utils.FileUpload;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.Vo.MerchantInfoVo;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.manager.merchant.MerchantInfoManager;
import com.mbr.admin.manager.security.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class MercahntInfoManagerImpl implements MerchantInfoManager {

    @Resource
    private MerchantInfoDao merchantInfoDao;

    @Resource
    private ChannelManager channelManager;
    @Resource
    private FileUpload fileUpload;

    @Value("${image_url}")
    private String image_url;

    @Override
    public List<MerchantInfo> queryList(String nameSearch,String idSearch) {
        List<MerchantInfo> merchantInfoList = merchantInfoDao.queryList(nameSearch, idSearch);
        for(int i=0;i<merchantInfoList.size();i++){
            if(merchantInfoList.get(i).getLogoBill()!=null&&merchantInfoList.get(i).getLogoBill()!=""){
                merchantInfoList.get(i).setLogoBill(image_url+merchantInfoList.get(i).getLogoBill());
            }
        }
        return merchantInfoList;
    }

    @Override
    public List<Map<String, Object>> queryChannel() {
        return channelManager.findAllChannel();
    }


    @Override
    public int deleteMerchantInfo(String id) {
        return merchantInfoDao.deleteById(id);
    }

    @Override
    public String queryRsaPublic(String id) {
        return merchantInfoDao.queryRsaPublic(id);
    }

    @Override
    public String queryRsaPrivate(String id) {
        return merchantInfoDao.queryRsaPrivate(id);
    }

    @Override
    public MerchantInfo queryById(String id) {

        return merchantInfoDao.queryById(id);
    }

    @Override
    public int updateChannelById(String channel,String id) {
        return merchantInfoDao.updateMerchantInfoChannel(channel,id);
    }

    @Override
    public List<Map<String, Object>> queryIsShow() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id","0");
        map.put("text","是");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id","1");
        map1.put("text","否");
        list.add(map);
        list.add(map1);
        return list;
    }

    @Override
    public List<Map<String, Object>> queryStatus() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id","0");
        map.put("text","未审核");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id","1");
        map1.put("text","审核通过");
        Map<String,Object> map2 = new HashMap<>();
        map2.put("id","2");
        map2.put("text","审核不通过");
        list.add(map);
        list.add(map1);
        list.add(map2);
        return list;
    }

    @Override
    public String addOrUpdate(MerchantInfoVo merchantInfoVo, HttpServletRequest request) {

        String id = null;
        if(merchantInfoVo.getId()==null){
            id = new TimestampPkGenerator().next(getClass()).toString();
        }
        String imgUrl = null;
        Map<String, MultipartFile> mapFiles = fileUpload.getFile(request);
        if(mapFiles!=null){
            String json = fileUpload.httpClientUploadFile(mapFiles);
            Map map = JSONObject.toJavaObject(JSON.parseObject(json), Map.class);
            if((Integer)map.get("code")==200){
                Map<String,String> mapRequest = (Map<String,String>)map.get("data");
                for (Map.Entry<String,String> entry:mapRequest.entrySet()) {
                    imgUrl = entry.getValue();
                }
            }
            if(imgUrl==null){
                return "imgFailed";
            }
        }
        MerchantInfo merchantInfo = createMerchantInfo(merchantInfoVo, id, imgUrl);
        System.out.println(merchantInfo);
        if(merchantInfoVo.getId()==null){
            int insert = merchantInfoDao.insert(merchantInfo);

            if (insert > 0) {
                return "success";
            }else{
                return "insertFailed";
            }
        }else{
            int i = merchantInfoDao.updateById(merchantInfo);
            if(i>0){
                return "success";
            }else{
                return "updateFailed";
            }
        }

    }

    public MerchantInfo createMerchantInfo(MerchantInfoVo merchantInfoVo,String id,String imgUrl){
        MerchantInfo merchantInfo = new MerchantInfo();
        if(id!=null){
            merchantInfo.setId(id);
            SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            merchantInfo.setCreateUserName(securityUserDetails.getUsername());
            merchantInfo.setCreateTime(new Date());

        }else{
            merchantInfo.setId(merchantInfoVo.getId().toString());
            merchantInfo.setCreateUserName(merchantInfoVo.getCreateUserName());
            SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            merchantInfo.setUpdateUserName(securityUserDetails.getUsername());
            merchantInfo.setCreateTime(merchantInfoVo.getCreateTime());
            merchantInfo.setUpdateTime(new Date());
        }
        if(imgUrl!=null){
            merchantInfo.setLogoBill(imgUrl);
        }else{
            merchantInfo.setLogoBill(merchantInfoVo.getOldImg());
        }
        merchantInfo.setName(merchantInfoVo.getName());
        merchantInfo.setDescription(merchantInfoVo.getDescription());
        merchantInfo.setWebsite(merchantInfoVo.getWebsite());
        merchantInfo.setAddress(merchantInfoVo.getAddress());
        merchantInfo.setStatus(merchantInfoVo.getStatus());
        merchantInfo.setRsaPrivate(merchantInfoVo.getRsaPrivate());
        merchantInfo.setRsaPublic(merchantInfoVo.getRsaPublic());
        merchantInfo.setIsShow(merchantInfoVo.getIsShow());
        merchantInfo.setChannel(merchantInfoVo.getChannel());
        return merchantInfo;
    }


}
