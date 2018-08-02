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
import com.mbr.admin.manager.merchant.MerchantVsResourceManager;
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
    private MerchantVsResourceManager merchantVsResourceManager;
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
    public String auditMerchant(MerchantCoinTmp merchantCoinTmp) throws MerchantException {
        //查询出商户信息
        MerchantInfo merchantInfo = merchantInfoManager.queryById(merchantCoinTmp.getMerchantId());
        Channel channel = createChannel(merchantCoinTmp);
        if(merchantInfo!=null){
            //更新临时表的审核状态以及渠道号
            int i = merchantCoinTmpDao.updateStatusByAudit(1, merchantCoinTmp.getId(),channel.getChannel());
            if(i>0){
                //更新商户表的渠道号信息
                int j = merchantInfoManager.updateChannelById(channel.getChannel(),merchantCoinTmp.getMerchantId());
                if(j>0){
                    //判断merchantCoin中是否存在相同地址相同币种的充值记录
                    MerchantCoin merchantCoin1 = merchantCoinDao.queryCoin(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId());
                    int merchantCoinInsert = 0;
                    if(merchantCoin1==null){
                        merchantCoinInsert = merchantCoinDao.insertMerchantCoin(createMerchantCoin(merchantCoinTmp,channel.getChannel()));
                    }else{
                        merchantCoinInsert =  merchantCoinDao.updateCoin(merchantCoinTmp.getMerchantId(),channel.getChannel(), merchantCoinTmp.getCoinId(),merchantCoinTmp.getRechargeAddress());
                    }

                    if(merchantCoinInsert>0){
                        //判断withDraw中是否已存在相同商户id和币id的信息
                        WithDraw withDraw1 = withDrawDao.queryWithdraw(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId());
                        int withdrawInsert = 0;
                        if(withDraw1==null){
                            withdrawInsert = withDrawDao.insertWithdraw(createWithdraw(merchantCoinTmp,channel.getChannel()));
                        }else{
                            withdrawInsert=withDrawDao.updateWithdraw(merchantCoinTmp.getMerchantId(), merchantCoinTmp.getCoinId(),channel.getChannel(),merchantCoinTmp.getWithdrawAddress());
                        }

                        if(withdrawInsert>0){
                            //查看权限表中是否存在相同数据
                            List<MerchantVsResource> merchantVsResourceList = merchantVsResourceDao.queryMerchantVsResource(merchantCoinTmp.getMerchantId());
                            int k = 0;
                            if(merchantVsResourceList.size()==0){
                                //添加权限
                                k= merchantVsResourceManager.initMerchantVsResource(merchantInfo.getId(), merchantInfo.getChannel());
                            }else{
                               k = merchantVsResourceDao.updateChannel(merchantCoinTmp.getMerchantId(),channel.getChannel());
                            }
                           if(k>0){
                               List<Channel> channelList = channelRepository.findByChannelAndMerchantId(Long.parseLong(merchantCoinTmp.getOldChannel()), merchantCoinTmp.getMerchantId());
                               Channel channelInsert = null;
                               //如果已存在商户和渠道的对应关系记录，就修改
                               if(channelList!=null&&channelList.size()>0){
                                   for(int h =0;h<channelList.size();h++){
                                       Channel channel1 = new Channel();
                                       channel1.setId(channelList.get(h).getId());
                                       channel1.setChannel(channel.getChannel());
                                       channel1.setMerchantId(merchantCoinTmp.getMerchantId());
                                       //查询修改后的渠道号的appName
                                       List<Channel> channelListAppName = channelRepository.findByChannel(merchantCoinTmp.getChannel());
                                       String appName = "";
                                       if( channelListAppName!=null&&channelListAppName.size()>0){
                                           appName = channelListAppName.get(0).getAppName();
                                       }
                                       channel1.setAppName(appName);
                                       channel1.setSystemName(merchantInfo.getName());
                                       channel1.setStatus(0);
                                       channel1.setCreateTime(channelList.get(h).getCreateTime());
                                       channel1.setUpdateTime(channelList.get(h).getUpdateTime());
                                       channelInsert = channelRepository.save(channel1);
                                   }
                               }else{
                                   //不存在就新增
                                     channelInsert = channelRepository.save(channel);
                               }
                               if(channelInsert!=null){
                                   return "success";
                               }else{
                                   throw new MerchantException("渠道号添加失败");
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

    //创建渠道对象
    public Channel createChannel(MerchantCoinTmp merchantCoinTmp){
        MerchantInfo merchantInfo = merchantInfoManager.queryById(merchantCoinTmp.getMerchantId());
        Channel channel = new Channel();
        if(merchantCoinTmp.getChannel()==null){
            Long channelNumber = new TimestampPkGenerator().next(getClass());
            channel.setChannel(channelNumber);
        }else{
            channel.setChannel(merchantCoinTmp.getChannel());
        }
        Long channelId = new TimestampPkGenerator().next(getClass());
        List<Channel> channelList = channelRepository.findByChannel(merchantCoinTmp.getChannel());
        String appName = "";
        if(channelList!=null&&channelList.size()>0){
            appName = channelList.get(0).getAppName();
        }
        channel.setAppName(appName);
        channel.setId(channelId);
        channel.setSystemName(merchantInfo.getName());
        channel.setStatus(0);
        channel.setMerchantId(merchantInfo.getId());
        channel.setCreateTime(new Date());
        return channel;
    }

}
