package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.common.utils.DateUtil;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantCoinDao;
import com.mbr.admin.dao.merchant.MerchantCoinTmpDao;
import com.mbr.admin.dao.merchant.MerchantVsResourceDao;
import com.mbr.admin.dao.merchant.WithDrawDao;
import com.mbr.admin.domain.merchant.*;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.manager.merchant.MerchantCoinTmpManager;
import com.mbr.admin.manager.merchant.MerchantInfoManager;
import com.mbr.admin.manager.security.SecurityUserDetails;
import com.mbr.admin.repository.ChannelRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MerchantCoinTmpManagerImpl implements MerchantCoinTmpManager {

    @Resource
    private MerchantCoinTmpDao merchantCoinTmpDao;
    @Resource
    private ChannelManager channelManager;
    @Resource
    private MerchantInfoManager merchantInfoManager;
    @Resource
    private MerchantCoinDao merchantCoinDao;
    @Resource
    private WithDrawDao withDrawDao;
    @Resource
    private MerchantVsResourceDao merchantVsResourceDao;
    @Resource
    private ChannelRepository channelRepository;
    @Override
    public List<MerchantCoinTmp> queryList(String merchantIdSearch) {
        List<MerchantCoinTmp> merchantCoinTmpList = merchantCoinTmpDao.queryList(merchantIdSearch);
        for(int i=0;i<merchantCoinTmpList.size();i++){
            if(merchantCoinTmpList.get(i).getCreateTime()!=null&&merchantCoinTmpList.get(i).getCreateTime()!=""){
                merchantCoinTmpList.get(i).setCreateTime(merchantCoinTmpList.get(i).getCreateTime().substring(0,merchantCoinTmpList.get(i).getCreateTime().length()-2));
            }
            if(merchantCoinTmpList.get(i).getUpdateTime()!=null&&merchantCoinTmpList.get(i).getUpdateTime()!=""){
                merchantCoinTmpList.get(i).setUpdateTime(merchantCoinTmpList.get(i).getUpdateTime().substring(0,merchantCoinTmpList.get(i).getUpdateTime().length()-2));
            }
        }
        return merchantCoinTmpList;
    }

    @Override
    public List<Map<String, Object>> queryChannel() {

        return channelManager.findAllChannel();
    }

    @Override
    public MerchantCoinTmp queryById(String id) {
        return merchantCoinTmpDao.queryById(id);
    }

    @Override
    @Transactional(rollbackFor = MerchantException.class)
    public Object auditMerchantNoChannel(MerchantCoinTmp merchantCoinTmp) throws MerchantException {
        //查询出商户信息
        MerchantInfo merchantInfo = merchantInfoManager.queryById(merchantCoinTmp.getMerchantId());
        if(merchantInfo!=null){
            //先创建渠道号
            Channel channel = new Channel();
            Long channelId = new TimestampPkGenerator().next(getClass());
            Long channelNumber = new TimestampPkGenerator().next(getClass());
            channel.setId(channelId);
            channel.setChannel(channelNumber);
            channel.setSystemName(merchantInfo.getName());
            channel.setStatus(0);
            channel.setMerchantId(merchantInfo.getId());
            channel.setCreateTime(new Date());
            //更新临时表的审核状态以及渠道号
            int i = merchantCoinTmpDao.updateStatusByAudit(1, merchantCoinTmp.getId(),channelNumber);
            if(i>0){
                //更新商户的渠道号信息
                int j = merchantInfoManager.updateChannelById(channelNumber,merchantCoinTmp.getMerchantId());
                if(j>0){
                    //判断merchantCoin中是否存在
                    MerchantCoin merchantCoin1 = merchantCoinDao.queryCoin(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId());
                    int merchantCoinInsert = 0;
                    if(merchantCoin1==null){

                        merchantCoinInsert = merchantCoinDao.insertMerchantCoin(createMerchantCoin(merchantCoinTmp,channelNumber));
                    }else{
                        merchantCoinInsert =  merchantCoinDao.updateCoin(merchantCoinTmp.getMerchantId(),channelNumber, merchantCoinTmp.getCoinId(),merchantCoinTmp.getRechargeAddress());
                    }

                    if(merchantCoinInsert>0){
                        //判断withDraw中是否已存在相同商户id和币id的信息
                        WithDraw withDraw1 = withDrawDao.queryWithdraw(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId());
                        int withdrawInsert = 0;
                        if(withDraw1==null){
                            withdrawInsert = withDrawDao.insertWithdraw(createWithdraw(merchantCoinTmp,channelNumber));
                        }else{
                            withdrawInsert=withDrawDao.updateWithdraw(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId(),channelNumber,merchantCoinTmp.getWithdrawAddress());
                        }

                        if(withdrawInsert>0){
                            //查看权限表中是否存在相同数据
                            List<MerchantVsResource> merchantVsResourceList = merchantVsResourceDao.queryMerchantVsResource(merchantCoinTmp.getMerchantId());
                            int k = 0;
                            if(merchantVsResourceList.size()==0){
                                //添加权限
                                k= initMerchantVsResource(merchantInfo.getId(), merchantInfo.getChannel());
                            }else{
                               k = merchantVsResourceDao.updateChannel(merchantCoinTmp.getMerchantId(),channelNumber);
                            }
                           if(k>0){
                               Channel channelInsert = channelRepository.insert(channel);
                               if(channelInsert!=null){
                                   return "success";
                               }else{
                                   throw new MerchantException("渠道号添加势失败");
                               }
                           }else{
                               throw new MerchantException("商户资源配置失败");
                           }
                        }else{
                            throw new MerchantException("商户提现地址添加失败");
                        }
                    }else{
                        throw new MerchantException("商户支付地址添加失败");
                    }

                }else{
                    throw new MerchantException("更新商户渠道号失败");
                }
            }else {
                throw new MerchantException("更新临时表失败");
            }
        }else{
            throw new MerchantException("商户信息不存在");
        }

    }

    @Override
    @Transactional(rollbackFor = MerchantException.class)
    public Object auditMercahntWithChannel(MerchantCoinTmp merchantCoinTmp) throws MerchantException {
        //查询出商户信息
        MerchantInfo merchantInfo = merchantInfoManager.queryById(merchantCoinTmp.getMerchantId());
        if(merchantInfo!=null){
            //更新临时表的审核状态以及渠道号
            int i = merchantCoinTmpDao.updateStatusByAudit(1, merchantCoinTmp.getId(),merchantCoinTmp.getChannel());
            if(i>0) {
                //更新商户的渠道号信息
                int j = merchantInfoManager.updateChannelById(merchantCoinTmp.getChannel(), merchantCoinTmp.getMerchantId());
                if (j > 0) {
                    //判断merchantCoin中是否存在
                    MerchantCoin merchantCoin1 = merchantCoinDao.queryCoin(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId());
                    int merchantCoinInsert = 0;
                    if(merchantCoin1==null){
                        //添加MerchantCoin
                        merchantCoinInsert = merchantCoinDao.insertMerchantCoin(createMerchantCoin(merchantCoinTmp,null));
                    }else{
                        merchantCoinInsert =  merchantCoinDao.updateCoin(merchantCoinTmp.getMerchantId(),merchantCoinTmp.getChannel(), merchantCoinTmp.getCoinId(),merchantCoinTmp.getRechargeAddress());
                    }

                    if (merchantCoinInsert > 0) {
                        //判断withDraw中是否已存在相同商户id和币id的信息
                        WithDraw withDraw1 = withDrawDao.queryWithdraw(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId());
                        int withdrawInsert = 0;

                        if(withDraw1==null){
                            withdrawInsert = withDrawDao.insertWithdraw(createWithdraw(merchantCoinTmp,null));
                        }else{
                            withdrawInsert=withDrawDao.updateWithdraw(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId(),merchantCoinTmp.getChannel(),merchantCoinTmp.getWithdrawAddress());
                        }
                        if (withdrawInsert > 0) {
                            //查看权限表中是否存在相同数据
                            List<MerchantVsResource> merchantVsResourceList = merchantVsResourceDao.queryMerchantVsResource(merchantCoinTmp.getMerchantId());
                            int k = 0;
                            if(merchantVsResourceList.size()==0){
                                //添加权限
                                k= initMerchantVsResource(merchantInfo.getId(), merchantInfo.getChannel());
                            }else{
                                k = merchantVsResourceDao.updateChannel(merchantCoinTmp.getMerchantId(),merchantCoinTmp.getChannel());
                            }
                            if(k>0){
                                return "success";
                            }else{
                                throw new MerchantException("商户资源配置失败");
                            }
                        }else{
                            throw new MerchantException("商户提现地址添加失败");
                        }
                    }else{
                        throw new MerchantException("商户支付地址添加失败");
                    }
                }else{
                    throw new MerchantException("更新商户渠道号失败");
                }
            }else {
                throw new MerchantException("更新临时表失败");
            }
        }else{
            throw new MerchantException("商户信息不存在");
        }

    }

    @Override
    public int auditMercahntNotPass(String id,int status) {

        return merchantCoinTmpDao.updateStatusNotPass(status,id);

    }

    //创建提现对象
    public WithDraw createWithdraw(MerchantCoinTmp merchantCoinTmp,Long channel){
        //添加withdraw
        WithDraw withDraw = new WithDraw();
        withDraw.setId(new TimestampPkGenerator().next(getClass()));
        withDraw.setCreateTime(DateUtil.formatDateTime(new Date()));
        withDraw.setAddress(merchantCoinTmp.getWithdrawAddress());
        withDraw.setMerchantId(merchantCoinTmp.getMerchantId());
        withDraw.setCoinId(Long.parseLong(merchantCoinTmp.getCoinId()));
        withDraw.setStatus(0);
        if(channel==null){
            withDraw.setChannel(merchantCoinTmp.getChannel());
        }else{
            withDraw.setChannel(channel);
        }

        return withDraw;
    }

    //创建充值对象
    public MerchantCoin createMerchantCoin(MerchantCoinTmp merchantCoinTmp,Long channel){
        MerchantCoin merchantCoin = new MerchantCoin();
        merchantCoin.setId(new TimestampPkGenerator().next(getClass()));
        merchantCoin.setCoinId(Long.parseLong(merchantCoinTmp.getCoinId()));
        merchantCoin.setMerchantId(merchantCoinTmp.getMerchantId());
        merchantCoin.setStatus(0);
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        merchantCoin.setCreateUserName(securityUserDetails.getUsername());
        merchantCoin.setCreateTime(DateUtil.formatDateTime(new Date()));
        merchantCoin.setAddress(merchantCoinTmp.getRechargeAddress());
        merchantCoin.setTokenAddress(merchantCoinTmp.getTokenAddress());
        merchantCoin.setCoinName(merchantCoinTmp.getCoinName());
        if(channel==null){
            merchantCoin.setChannel(merchantCoinTmp.getChannel());
        }else{
            merchantCoin.setChannel(channel);
        }

        return merchantCoin;
    }

    /**
     * 为新建的商家初始化资源
     *
     * @param merchantId
     */
    private int initMerchantVsResource(String merchantId, Long channelId) {
        SecurityUserDetails securityUserDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final MerchantVsResource re3 = new MerchantVsResource();
        re3.setId(new TimestampPkGenerator().next(getClass()).toString());
        re3.setMerchantId(merchantId);
        re3.setChannel(channelId);
        re3.setCreateTime(DateUtil.formatDateTime(new Date()));
        re3.setCreateUserName(securityUserDetails.getUsername());
        re3.setResourceId(3L);
        re3.setStatus(0);
        final MerchantVsResource re4 = new MerchantVsResource();
        re4.setId(new TimestampPkGenerator().next(getClass()).toString());
        re4.setMerchantId(merchantId);
        re4.setChannel(channelId);
        re4.setCreateTime(DateUtil.formatDateTime(new Date()));
        re4.setCreateUserName(securityUserDetails.getUsername());
        re4.setResourceId(4L);
        re4.setStatus(0);
        final MerchantVsResource re11 = new MerchantVsResource();
        re11.setId(new TimestampPkGenerator().next(getClass()).toString());
        re11.setMerchantId(merchantId);
        re11.setChannel(channelId);
        re11.setCreateTime(DateUtil.formatDateTime(new Date()));
        re11.setCreateUserName(securityUserDetails.getUsername());
        re11.setResourceId(11L);
        re11.setStatus(0);
        final MerchantVsResource re12 = new MerchantVsResource();
        re12.setId(new TimestampPkGenerator().next(getClass()).toString());
        re12.setMerchantId(merchantId);
        re12.setChannel(channelId);
        re12.setCreateTime(DateUtil.formatDateTime(new Date()));
        re12.setCreateUserName(securityUserDetails.getUsername());
        re12.setResourceId(12L);
        re12.setStatus(0);
        final MerchantVsResource re22 = new MerchantVsResource();
        re22.setId(new TimestampPkGenerator().next(getClass()).toString());
        re22.setMerchantId(merchantId);
        re22.setChannel(channelId);
        re22.setCreateTime(DateUtil.formatDateTime(new Date()));
        re22.setCreateUserName(securityUserDetails.getUsername());
        re22.setResourceId(22L);
        re22.setStatus(0);
        final MerchantVsResource re23 = new MerchantVsResource();
        re23.setId(new TimestampPkGenerator().next(getClass()).toString());
        re23.setMerchantId(merchantId);
        re23.setChannel(channelId);
        re23.setCreateTime(DateUtil.formatDateTime(new Date()));
        re23.setCreateUserName(securityUserDetails.getUsername());
        re23.setResourceId(23L);
        re23.setStatus(0);
        final MerchantVsResource re24 = new MerchantVsResource();
        re24.setId(new TimestampPkGenerator().next(getClass()).toString());
        re24.setMerchantId(merchantId);
        re24.setChannel(channelId);
        re24.setCreateTime(DateUtil.formatDateTime(new Date()));
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
