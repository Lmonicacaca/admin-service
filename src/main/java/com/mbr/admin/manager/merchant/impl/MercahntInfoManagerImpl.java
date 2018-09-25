package com.mbr.admin.manager.merchant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mbr.admin.common.utils.DateUtil;
import com.mbr.admin.common.utils.FileUpload;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantCoinDao;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.dao.merchant.MerchantVsResourceDao;
import com.mbr.admin.dao.merchant.WithDrawDao;
import com.mbr.admin.domain.merchant.*;
import com.mbr.admin.domain.merchant.Vo.MerchantInfoVo;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.manager.merchant.MerchantInfoManager;
import com.mbr.admin.manager.merchant.MerchantVsResourceManager;
import com.mbr.admin.manager.security.SecurityUserDetails;
import com.mbr.admin.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
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
    private ChannelRepository channelRepository;
    @Resource
    private FileUpload fileUpload;
    @Resource
    private MerchantVsResourceManager merchantVsResourceManager;
    @Resource
    private MerchantCoinDao merchantCoinDao;
    @Resource
    private WithDrawDao withDrawDao;

    @Value("${image_url}")
    private String image_url;

    @Override
    public List<MerchantInfoVo> queryList(String nameSearch, String idSearch) {
        List<MerchantInfoVo> merchantInfoList = merchantInfoDao.queryList(nameSearch, idSearch);
        for (int i = 0; i < merchantInfoList.size(); i++) {

            if (merchantInfoList.get(i).getLogoBill() != null && merchantInfoList.get(i).getLogoBill() != "") {
                merchantInfoList.get(i).setLogoBill(image_url + merchantInfoList.get(i).getLogoBill());
            }
            if(merchantInfoList.get(i).getCreateTime()!=null&&merchantInfoList.get(i).getCreateTime()!=""){
                merchantInfoList.get(i).setCreateTime(merchantInfoList.get(i).getCreateTime().substring(0,merchantInfoList.get(i).getCreateTime().length()-2));
            }
            if(merchantInfoList.get(i).getUpdateTime()!=null&&merchantInfoList.get(i).getUpdateTime()!=""){
                merchantInfoList.get(i).setUpdateTime(merchantInfoList.get(i).getUpdateTime().substring(0,merchantInfoList.get(i).getUpdateTime().length()-2));
            }

        }
        return merchantInfoList;
    }

    @Override
    public List<Map<String, Object>> queryChannel() {
        return channelManager.findAllChannel();
    }


    @Override
    @Transactional(rollbackFor = MerchantException.class)
    public boolean deleteMerchantInfo(String id) throws MerchantException{
        MerchantInfo merchantInfo = merchantInfoDao.queryById(id);
        Long channel = merchantInfo.getChannel();
        MerchantInfo merchantInfo1 = merchantInfoDao.queryById(id);
        if(merchantInfo1!=null){
            //删除商户
            int deleteMerchant = merchantInfoDao.deleteById(id);
            if(deleteMerchant>0){
                List<MerchantVsResource> merchantVsResources = merchantVsResourceDao.queryByMerchantAndResource(id, channel);
                if(merchantVsResources!=null&&merchantVsResources.size()>0){
                    //删除权限
                    int deleteMerchantVsResource = merchantVsResourceDao.deleteByMerchantIdAndChannel(id, channel);
                    if(deleteMerchantVsResource>0){
                        List<MerchantCoin> merchantCoins = merchantCoinDao.queryByMerchantIdAndChannel(id, channel);
                        if(merchantCoins!=null&&merchantCoins.size()>0){
                            //删除充值地址和提现地址
                            int deleteCoinAddr = merchantCoinDao.deleteByMerchantIdAndChannel(id, channel);
                            if(deleteCoinAddr>0){
                                List<WithDraw> withDraws = withDrawDao.queryByMerchantIdAndChannel(id, channel);
                                if(withDraws!=null&&withDraws.size()>0){
                                    int deleteWithDraw = withDrawDao.deleteByMerchantIdAndChannel(id, channel);
                                    if(deleteWithDraw>0){
                                        List<Channel> byChannelAndMerchantId = channelRepository.findByChannelAndMerchantId(channel, id);
                                        if(byChannelAndMerchantId!=null&&byChannelAndMerchantId.size()>0){
                                            //删除商户号和渠道号
                                            int deleteChannel = channelRepository.deleteByMerchantIdAndChannel(id, channel);
                                            if(deleteChannel>0){
                                                return true;
                                            }else {
                                                throw new MerchantException("删除渠道号失败!");
                                            }
                                        }else {
                                            return true;
                                        }

                                    }else {
                                        throw new MerchantException("删除提现地址失败！");
                                    }
                                }else {
                                    return true;
                                }

                            }else {
                                throw new MerchantException("删除充值地址失败！");
                            }
                        }else{
                            return true;
                        }

                    }else {
                        throw new MerchantException("删除权限失败！");
                    }
                }else {
                    return true;
                }
            }else{
                throw new MerchantException("删除商户失败！");
            }

        }else{
            throw new MerchantException("刪除不存在！");
        }

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
    public int updateChannelById(Long channel, String id) {
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
        Channel channel = createChannel(merchantInfo,merchantInfoVo);
        merchantInfo.setChannel(channel.getChannel());
        if(merchantInfoVo.getId()==null){
            //添加商户信息
            int insert = merchantInfoDao.insert(merchantInfo);
            if (insert > 0) {
                System.out.println("添加商户成功********************");
                //为商户添加权限
                int i = merchantVsResourceManager.initMerchantVsResource(merchantInfo.getId(), channel.getChannel());
                    if(i>0){
                        //添加渠道表信息
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
                throw new MerchantException("添加商户失败");
            }
        }else{
            //修改时需要新建一个渠道号
            if(merchantInfoVo.getChannel()==null){
                Long channelNumber = new TimestampPkGenerator().next(getClass());
                merchantInfoVo.setChannel(channelNumber);
                merchantInfo.setChannel(channelNumber);
            }
            //根据渠道号和商户号查询出所有的渠道信息
            List<Channel> channelList = channelManager.queryChannelByMerchantIdAndChannel(merchantInfoVo.getOldChannel(), merchantInfoVo.getId());

            int i = merchantInfoDao.updateById(merchantInfo);//修改商户表
            if(i>0){
                int updateChannel = merchantVsResourceDao.updateChannel(merchantInfo.getId(), merchantInfo.getChannel());//修改权限表
                if(updateChannel>0){
                    int update = channelManager.updateChannel(channelList,merchantInfoVo);//修改渠道表，修改渠道和商户名，多条数据一起修改。
                    if(update>0){
                        return "success";
                    }else{
                        throw new MerchantException("更新渠道号失败");
                    }
                }else{
                    throw new MerchantException("更新权限失败");
                }

            }else{
               throw new MerchantException("更新商户信息失败");
            }
        }
    }

    /**
     * 审核商户
     * @param merchantInfo
     * @return
     */
    @Override
    public String auditMerchant(MerchantInfo merchantInfo) {
        //若渠道号为空，则生成新的渠道号
        if(merchantInfo.getChannel()==null||merchantInfo.getChannel().equals("")){
            Long channelNumber = new TimestampPkGenerator().next(getClass());
            merchantInfo.setChannel(channelNumber);
        }
        //设置商户审核状态为1，表示审核通过
        merchantInfo.setAudit(1);
        int i = merchantInfoDao.auditMerchantInfo(merchantInfo);
        if(i>0){
            return "success";
        }
        return "failed";
    }

    /**
     * 创建商户
     * @param merchantInfoVo
     * @param id
     * @param imgUrl
     * @return
     */
    public MerchantInfo createMerchantInfo(MerchantInfoVo merchantInfoVo, String id, String imgUrl) {
        MerchantInfo merchantInfo = new MerchantInfo();
        if (id != null) {
            merchantInfo.setId(id);
            SecurityUserDetails securityUserDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            merchantInfo.setCreateUserName(securityUserDetails.getUsername());
            merchantInfo.setCreateTime(DateUtil.formatDateTime(new Date()));

        } else {
            merchantInfo.setId(merchantInfoVo.getId().toString());
            merchantInfo.setCreateUserName(merchantInfoVo.getCreateUserName());
            SecurityUserDetails securityUserDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            merchantInfo.setUpdateUserName(securityUserDetails.getUsername());

            merchantInfo.setCreateTime(merchantInfoVo.getCreateTime());
            merchantInfo.setUpdateTime(DateUtil.formatDateTime(new Date()));
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
        if(merchantInfoVo.getChannel()!=null&&!merchantInfoVo.getChannel().equals("")){
            merchantInfo.setChannel(merchantInfoVo.getChannel());
        }
        return merchantInfo;
    }

    /**
     * 创建渠道号
     * @param merchantInfo
     * @param merchantInfoVo
     * @return
     */
    public Channel createChannel(MerchantInfo merchantInfo,MerchantInfoVo merchantInfoVo){
        Channel channel = new Channel();
        if(merchantInfoVo.getId()==null){
            //新增的情况
            if(merchantInfo.getChannel()==null){
                //需要新建渠道号的
                Long channelId = new TimestampPkGenerator().next(getClass());
                Long channelNumber = new TimestampPkGenerator().next(getClass());
                channel.setId(channelId);
                channel.setChannel(channelNumber);
                channel.setAppName("");
            }else{
                //使用已有的渠道号
                Long channelId = new TimestampPkGenerator().next(getClass());
                channel.setId(channelId);
                channel.setChannel(merchantInfo.getChannel());
                List<Channel> channelList = channelRepository.findByChannel(merchantInfoVo.getChannel());
                String appName = channelList.get(0).getAppName();
                channel.setAppName(appName);
            }
        }else{
            //修改的情况
            channel.setChannel(merchantInfoVo.getChannel());
        }
        channel.setCreateTime(new Date());
        channel.setUpdateTime(new Date());
        channel.setSystemName(merchantInfo.getName());
        channel.setStatus(0);
        channel.setMerchantId(merchantInfo.getId());
        return channel;
    }


}
