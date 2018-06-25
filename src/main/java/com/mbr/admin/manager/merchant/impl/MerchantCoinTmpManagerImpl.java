package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.common.utils.AuditMerchantException;
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

        return merchantCoinTmpDao.queryList(merchantIdSearch);
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
    @Transactional(rollbackFor = AuditMerchantException.class)
    public Object auditMerchantNoChannel(MerchantCoinTmp merchantCoinTmp) throws AuditMerchantException {
        //查询出商户信息
        MerchantInfo merchantInfo = merchantInfoManager.queryById(merchantCoinTmp.getMerchantId());
        if(merchantInfo!=null){
            //先创建渠道号
            Channel channel = new Channel();
            String channelId = new TimestampPkGenerator().next(getClass()).toString();
            String channelNumber = new TimestampPkGenerator().next(getClass()).toString();
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
                        //添加MerchantCoin
                        MerchantCoin merchantCoin = new MerchantCoin();
                        merchantCoin.setId(new TimestampPkGenerator().next(getClass()));
                        merchantCoin.setCoinId(Long.parseLong(merchantCoinTmp.getCoinId()));
                        merchantCoin.setMerchantId(merchantCoinTmp.getMerchantId());
                        merchantCoin.setStatus(0);
                        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
                        merchantCoin.setCreateUserName(securityUserDetails.getUsername());
                        merchantCoin.setCreateTime(new Date());
                        merchantCoin.setAddress(merchantCoinTmp.getRechargeAddress());
                        merchantCoin.setTokenAddress(merchantCoinTmp.getTokenAddress());
                        merchantCoin.setCoinName(merchantCoinTmp.getCoinName());
                        merchantCoin.setChannel(channelNumber);
                        merchantCoinInsert = merchantCoinDao.insertMerchantCoin(merchantCoin);
                    }else{
                        merchantCoinInsert =  merchantCoinDao.updateCoin(merchantCoinTmp.getMerchantId(),channelNumber, merchantCoinTmp.getCoinId(),merchantCoinTmp.getRechargeAddress());
                    }

                    if(merchantCoinInsert>0){
                        //判断withDraw中是否已存在相同商户id和币id的信息
                        WithDraw withDraw1 = withDrawDao.queryWithdraw(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId());
                        int withdrawInsert = 0;
                        if(withDraw1==null){
                            //添加withdraw
                            WithDraw withDraw = new WithDraw();
                            withDraw.setId(new TimestampPkGenerator().next(getClass()));
                            withDraw.setCreateTime(new Date());
                            withDraw.setAddress(merchantCoinTmp.getWithdrawAddress());
                            withDraw.setMerchantId(merchantCoinTmp.getMerchantId());
                            withDraw.setCoinId(Long.parseLong(merchantCoinTmp.getCoinId()));
                            withDraw.setStatus(0);
                            withDraw.setChannel(channelNumber);
                            withdrawInsert = withDrawDao.insertWithdraw(withDraw);
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
                                   throw new AuditMerchantException("渠道号添加势失败");
                               }
                           }else{
                               throw new AuditMerchantException("商户资源配置失败");
                           }
                        }else{
                            throw new AuditMerchantException("商户提现地址添加失败");
                        }
                    }else{
                        throw new AuditMerchantException("商户支付地址添加失败");
                    }

                }else{
                    throw new AuditMerchantException("更新商户渠道号失败");
                }
            }else {
                throw new AuditMerchantException("更新临时表失败");
            }
        }else{
            throw new AuditMerchantException("商户信息不存在");
        }

    }

    @Override
    @Transactional(rollbackFor = AuditMerchantException.class)
    public Object auditMercahntWithChannel(MerchantCoinTmp merchantCoinTmp) throws AuditMerchantException {
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
                        MerchantCoin merchantCoin = new MerchantCoin();
                        merchantCoin.setId(new TimestampPkGenerator().next(getClass()));
                        merchantCoin.setCoinId(Long.parseLong(merchantCoinTmp.getCoinId()));
                        merchantCoin.setMerchantId(merchantCoinTmp.getMerchantId());
                        merchantCoin.setStatus(0);
                        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
                        merchantCoin.setCreateUserName(securityUserDetails.getUsername());
                        merchantCoin.setCreateTime(new Date());
                        merchantCoin.setAddress(merchantCoinTmp.getRechargeAddress());
                        merchantCoin.setTokenAddress(merchantCoinTmp.getTokenAddress());
                        merchantCoin.setCoinName(merchantCoinTmp.getCoinName());
                        merchantCoin.setChannel(merchantCoinTmp.getChannel());
                        merchantCoinInsert = merchantCoinDao.insertMerchantCoin(merchantCoin);
                    }else{
                        merchantCoinInsert =  merchantCoinDao.updateCoin(merchantCoinTmp.getMerchantId(),merchantCoinTmp.getChannel(), merchantCoinTmp.getCoinId(),merchantCoinTmp.getRechargeAddress());
                    }

                    if (merchantCoinInsert > 0) {
                        //判断withDraw中是否已存在相同商户id和币id的信息
                        WithDraw withDraw1 = withDrawDao.queryWithdraw(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId());
                        int withdrawInsert = 0;

                        if(withDraw1==null){
                            //添加withdraw
                            WithDraw withDraw = new WithDraw();
                            withDraw.setId(new TimestampPkGenerator().next(getClass()));
                            withDraw.setCreateTime(new Date());
                            withDraw.setAddress(merchantCoinTmp.getWithdrawAddress());
                            withDraw.setMerchantId(merchantCoinTmp.getMerchantId());
                            withDraw.setCoinId(Long.parseLong(merchantCoinTmp.getCoinId()));
                            withDraw.setStatus(0);
                            withDraw.setChannel(merchantCoinTmp.getChannel());
                            withdrawInsert = withDrawDao.insertWithdraw(withDraw);
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
                                throw new AuditMerchantException("商户资源配置失败");
                            }
                        }else{
                            throw new AuditMerchantException("商户提现地址添加失败");
                        }
                    }else{
                        throw new AuditMerchantException("商户支付地址添加失败");
                    }
                }else{
                    throw new AuditMerchantException("更新商户渠道号失败");
                }
            }else {
                throw new AuditMerchantException("更新临时表失败");
            }
        }else{
            throw new AuditMerchantException("商户信息不存在");
        }

    }

    @Override
    public int auditMercahntNotPass(String id,int status) {

        return merchantCoinTmpDao.updateStatusNotPass(status,id);
    }

    /**
     * 为新建的商家初始化资源
     *
     * @param merchantId
     */
    private int initMerchantVsResource(String merchantId, String channelId) {

        final MerchantVsResource re3 = new MerchantVsResource();
        re3.setMerchantId(merchantId);
        re3.setChannel(channelId);
        re3.setResourceId("3");
        re3.setStatus(0);
        final MerchantVsResource re4 = new MerchantVsResource();
        re4.setMerchantId(merchantId);
        re4.setChannel(channelId);
        re4.setResourceId("4");
        re4.setStatus(0);
        final MerchantVsResource re11 = new MerchantVsResource();
        re11.setMerchantId(merchantId);
        re11.setChannel(channelId);
        re11.setResourceId("11");
        re11.setStatus(0);
        final MerchantVsResource re12 = new MerchantVsResource();
        re12.setMerchantId(merchantId);
        re12.setChannel(channelId);
        re12.setResourceId("12");
        re12.setStatus(0);
        final MerchantVsResource re22 = new MerchantVsResource();
        re22.setMerchantId(merchantId);
        re22.setChannel(channelId);
        re22.setResourceId("22");
        re22.setStatus(0);
        final MerchantVsResource re23 = new MerchantVsResource();
        re23.setMerchantId(merchantId);
        re23.setChannel(channelId);
        re23.setResourceId("23");
        re23.setStatus(0);
        final MerchantVsResource re24 = new MerchantVsResource();
        re24.setMerchantId(merchantId);
        re24.setChannel(channelId);
        re24.setResourceId("24");
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
