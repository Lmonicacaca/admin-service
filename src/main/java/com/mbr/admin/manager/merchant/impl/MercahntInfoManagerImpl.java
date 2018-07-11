package com.mbr.admin.manager.merchant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mbr.admin.common.utils.FileUpload;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.dao.merchant.MerchantVsResourceDao;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.MerchantVsResource;
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
    private MerchantVsResourceDao merchantVsResourceDao;
    @Resource
    private ChannelManager channelManager;
    @Resource
    private FileUpload fileUpload;

    @Value("${image_url}")
    private String image_url;

    @Override
    public List<MerchantInfo> queryList(String nameSearch, String idSearch) {
        List<MerchantInfo> merchantInfoList = merchantInfoDao.queryList(nameSearch, idSearch);
        for (int i = 0; i < merchantInfoList.size(); i++) {
            if (merchantInfoList.get(i).getLogoBill() != null && merchantInfoList.get(i).getLogoBill() != "") {
                merchantInfoList.get(i).setLogoBill(image_url + merchantInfoList.get(i).getLogoBill());
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
    public int updateChannelById(String channel, String id) {
        return merchantInfoDao.updateMerchantInfoChannel(channel, id);
    }

    @Override
    public List<Map<String, Object>> queryIsShow() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "0");
        map.put("text", "是");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", "1");
        map1.put("text", "否");
        list.add(map);
        list.add(map1);
        return list;
    }

    @Override
    @Transactional(rollbackFor = MerchantException.class)
    public String addOrUpdate(MerchantInfoVo merchantInfoVo, HttpServletRequest request) throws MerchantException {

        String id = null;
        if (merchantInfoVo.getId() == null) {
            id = new TimestampPkGenerator().next(getClass()).toString();
        }
        String imgUrl = null;
        Map<String, MultipartFile> mapFiles = fileUpload.getFile(request);
        if (mapFiles != null) {
            String json = fileUpload.httpClientUploadFile(mapFiles);
            Map map = JSONObject.toJavaObject(JSON.parseObject(json), Map.class);
            if ((Integer) map.get("code") == 200) {
                Map<String, String> mapRequest = (Map<String, String>) map.get("data");
                for (Map.Entry<String, String> entry : mapRequest.entrySet()) {
                    imgUrl = entry.getValue();
                }
            }
            if (imgUrl == null) {
                return "imgFailed";
            }
        }
        MerchantInfo merchantInfo = createMerchantInfo(merchantInfoVo, id, imgUrl);
        Channel channel = new Channel();
        if(merchantInfo.getChannel()==null){
            String channelId = new TimestampPkGenerator().next(getClass()).toString();
            String channelNumber = new TimestampPkGenerator().next(getClass()).toString();
            channel.setId(channelId);
            channel.setChannel(channelNumber);
            channel.setSystemName(merchantInfo.getName());
            channel.setStatus(0);
            channel.setMerchantId(merchantInfo.getId());
            channel.setCreateTime(new Date());
            merchantInfo.setChannel(channel.getChannel());
            System.out.println(merchantInfo);
        }

        if(merchantInfoVo.getId()==null){
            //添加商户信息
            int insert = merchantInfoDao.insert(merchantInfo);
            if (insert > 0) {
                System.out.println("添加商户成功********************");
                if(merchantInfoVo.getChannel()==null){
                //为商户添加权限
                int i = initMerchantVsResource(merchantInfo.getId(), channel.getChannel());
                    if(i>0){
                        Channel insertChannel = channelManager.insertChannel(channel);
                        if(insertChannel!=null){
                            return "success";
                        }else{
                            throw new MerchantException("添加渠道号失败");
                        }
                    }else{
                        throw new MerchantException("商户权限分配失败");
                    }
                }else{
                    int i = initMerchantVsResource(merchantInfo.getId(), merchantInfo.getChannel());
                    if(i>0){
                        return "success";
                    }else{
                        throw new MerchantException("商户权限分配失败");
                    }
                }

            }else{
                throw new MerchantException("添加商户失败");
            }
        }else{
            int i = merchantInfoDao.updateById(merchantInfo);
            if(i>0){
                int updateChannel = merchantVsResourceDao.updateChannel(merchantInfo.getId(), merchantInfo.getChannel());
                if(updateChannel>0){
                    return "success";
                }else{
                    throw new MerchantException("更新权限失败");
                }

            }else{
               throw new MerchantException("更新商户信息失败");
            }
        }

    }

    public MerchantInfo createMerchantInfo(MerchantInfoVo merchantInfoVo, String id, String imgUrl) {
        MerchantInfo merchantInfo = new MerchantInfo();
        if (id != null) {
            merchantInfo.setId(id);
            SecurityUserDetails securityUserDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            merchantInfo.setCreateUserName(securityUserDetails.getUsername());
            merchantInfo.setCreateTime(new Date());

        } else {
            merchantInfo.setId(merchantInfoVo.getId().toString());
            merchantInfo.setCreateUserName(merchantInfoVo.getCreateUserName());
            SecurityUserDetails securityUserDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            merchantInfo.setUpdateUserName(securityUserDetails.getUsername());
            merchantInfo.setCreateTime(merchantInfoVo.getCreateTime());
            merchantInfo.setUpdateTime(new Date());
        }
        if (imgUrl != null) {
            merchantInfo.setLogoBill(imgUrl);
        } else {
            merchantInfo.setLogoBill(merchantInfoVo.getOldImg());
        }
        merchantInfo.setName(merchantInfoVo.getName());
        merchantInfo.setDescription(merchantInfoVo.getDescription());
        merchantInfo.setWebsite(merchantInfoVo.getWebsite());
        merchantInfo.setAddress(merchantInfoVo.getAddress());
        merchantInfo.setRsaPrivate(merchantInfoVo.getRsaPrivate());
        merchantInfo.setRsaPublic(merchantInfoVo.getRsaPublic());
        if (merchantInfoVo.getIsShow() == null) {
            merchantInfo.setIsShow(0);//默认显示在app
        } else {
            merchantInfo.setIsShow(merchantInfoVo.getIsShow());

        }
        merchantInfo.setStatus(0);
        merchantInfo.setAudit(1);//已审核
        merchantInfo.setChannel(merchantInfoVo.getChannel());
        return merchantInfo;
    }


    /**
     * 为新建的商家初始化资源
     *
     * @param merchantId
     */
    private int initMerchantVsResource(String merchantId, String channelId) {

        SecurityUserDetails securityUserDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final MerchantVsResource re3 = new MerchantVsResource();
        re3.setId(new TimestampPkGenerator().next(getClass()).toString());
        re3.setMerchantId(merchantId);
        re3.setChannel(channelId);
        re3.setCreateTime(new Date());
        re3.setCreateUserName(securityUserDetails.getUsername());
        re3.setResourceId(3L);
        re3.setStatus(0);
        final MerchantVsResource re4 = new MerchantVsResource();
        re4.setId(new TimestampPkGenerator().next(getClass()).toString());
        re4.setMerchantId(merchantId);
        re4.setChannel(channelId);
        re4.setCreateTime(new Date());
        re4.setCreateUserName(securityUserDetails.getUsername());
        re4.setResourceId(4L);
        re4.setStatus(0);
        final MerchantVsResource re11 = new MerchantVsResource();
        re11.setId(new TimestampPkGenerator().next(getClass()).toString());
        re11.setMerchantId(merchantId);
        re11.setChannel(channelId);
        re11.setCreateTime(new Date());
        re11.setCreateUserName(securityUserDetails.getUsername());
        re11.setResourceId(11L);
        re11.setStatus(0);
        final MerchantVsResource re12 = new MerchantVsResource();
        re12.setId(new TimestampPkGenerator().next(getClass()).toString());
        re12.setMerchantId(merchantId);
        re12.setChannel(channelId);
        re12.setCreateTime(new Date());
        re12.setCreateUserName(securityUserDetails.getUsername());
        re12.setResourceId(12L);
        re12.setStatus(0);
        final MerchantVsResource re22 = new MerchantVsResource();
        re22.setId(new TimestampPkGenerator().next(getClass()).toString());
        re22.setMerchantId(merchantId);
        re22.setChannel(channelId);
        re22.setCreateTime(new Date());
        re22.setCreateUserName(securityUserDetails.getUsername());
        re22.setResourceId(22L);
        re22.setStatus(0);
        final MerchantVsResource re23 = new MerchantVsResource();
        re23.setId(new TimestampPkGenerator().next(getClass()).toString());
        re23.setMerchantId(merchantId);
        re23.setChannel(channelId);
        re23.setCreateTime(new Date());
        re23.setCreateUserName(securityUserDetails.getUsername());
        re23.setResourceId(23L);
        re23.setStatus(0);
        final MerchantVsResource re24 = new MerchantVsResource();
        re24.setId(new TimestampPkGenerator().next(getClass()).toString());
        re24.setMerchantId(merchantId);
        re24.setChannel(channelId);
        re24.setCreateTime(new Date());
        re24.setCreateUserName(securityUserDetails.getUsername());
        re24.setResourceId(24L);
        re24.setStatus(0);

        final List<MerchantVsResource> list = new ArrayList<MerchantVsResource>(7);
        list.add(re3);
        list.add(re4);
        list.add(re11);
        list.add(re12);
        list.add(re22);
        list.add(re23);
        list.add(re24);
        int i = merchantVsResourceDao.insertList(list);
        return i;
    }

}
