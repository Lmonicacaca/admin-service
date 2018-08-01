package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.merchant.MerchantInfoDao;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.Vo.ChannelVo;
import com.mbr.admin.domain.merchant.Vo.MerchantInfoVo;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.repository.ChannelRepository;
import com.mongodb.WriteResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ChannelManagerImpl implements ChannelManager {

    @Resource
    private ChannelRepository channelRepository;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private MerchantInfoDao merchantInfoDao;

    @Override
    public List<Map<String, Object>> findAllChannel() {
        List<Channel> channelList = channelRepository.findAllByStatus(0);
        List<Channel> channelListDistinct = new ArrayList<>();
        List<Map<String,Object>> list = new ArrayList<>();
        for1:for(int i =0;i<channelList.size();i++){
            if(i==0){
                channelListDistinct.add(channelList.get(i));
            }else{
                for(int j=0;j<channelListDistinct.size();j++){
                    if(channelList.get(i).getChannel().equals(channelListDistinct.get(j).getChannel())){
                        continue for1;
                    }
                }
                channelListDistinct.add(channelList.get(i));
            }
        }

        for(int i=0;i<channelListDistinct.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",channelListDistinct.get(i).getChannel());
            map.put("text",channelListDistinct.get(i).getChannel());
            list.add(map);
        }
        return list;
    }



    @Override
    public Channel insertChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public Map<String,Object> queryAllChannel(String merchantName, String appName, Pageable page) {
        Query query = new Query();
        Criteria c = new Criteria();
        if(merchantName!=null&&appName!=null){
            c.andOperator(Criteria.where("systemName").regex(merchantName),Criteria.where("appName").regex(appName));
        }else if(merchantName!=null&&appName==null){
            c.andOperator(Criteria.where("systemName").regex(merchantName));
        }else if(merchantName==null&&appName!=null){
            c.andOperator(Criteria.where("appName").regex(appName));
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = new Sort(orders);
        if(null!=sort){
            query.with(sort);
        }
        query.addCriteria(c);
        long count = mongoTemplate.count(query, Channel.class);
        List<Channel> ChannelList = mongoTemplate.find(query.with(page), Channel.class);
        Map<String,Object> map = new HashMap<>();
        map.put("total",count);
        map.put("list",ChannelList);
        return map;
    }

    @Override
    public List<Map<String, Object>> queryMerchantId() {
        List<MerchantInfo> merchantInfoList = merchantInfoDao.selectAll();
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i=0;i<merchantInfoList.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",merchantInfoList.get(i).getId());
            map.put("text",merchantInfoList.get(i).getName());
            list.add(map);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = MerchantException.class)
    public String addOrUpdate(ChannelVo channelVo) throws MerchantException {
        Channel channel = createChannel(channelVo);
        System.out.println(channel);
        if(channelVo.getId()==null){
            //先查询是否有相同的数据存在
            Channel byChannelAndMerchantId = channelRepository.findByMerchantIdAndChannelAndAppName(channelVo.getMerchantId(),channelVo.getChannel(),channelVo.getAppName());
            if(byChannelAndMerchantId!=null){
                throw new MerchantException("该商户已有渠道号，请勿重复添加！");
            }

        }
        Channel insert = channelRepository.save(channel);
        if(insert!=null){
            return "success";
        }else{
            throw new MerchantException("添加失败，请重新添加");
        }
    }

    @Override
        public List<Channel> queryChannelByMerchantIdAndChannel(Long channel,Long merchantId) {
        List<Channel> channelList = channelRepository.findByChannelAndMerchantId(channel, merchantId.toString());
        return channelList;
    }

    @Override
    public int updateChannel(List<Channel> channelList, MerchantInfoVo merchantInfoVo) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        Update update = new Update();
        for(int i=0;i<channelList.size();i++){
            update.set("channel",merchantInfoVo.getChannel());
            update.set("systemName",merchantInfoVo.getName());
            criteria.andOperator(Criteria.where("id").is(channelList.get(i).getId()));
            query.addCriteria(criteria);
            WriteResult upsert = mongoTemplate.upsert(query, update, Channel.class);
        }
        return 1;
    }

    @Override
    public void deleteChannel(Long id) {
        channelRepository.delete(id);
    }

    @Override
    public Channel queryById(Long id) {
        return channelRepository.findOne(id);
    }


    public Channel createChannel(ChannelVo channelVo){
        Channel channel = new Channel();
        if(channelVo.getId()==null){
            Long channelId = new TimestampPkGenerator().next(getClass());
            channel.setId(channelId);
            channel.setCreateTime(new Date());
        }else{
            channel.setId(channelVo.getId());
            channel.setCreateTime(new Date());
            channel.setUpdateTime(new Date());
        }
        if(channelVo.getChannel()==null){
            Long channelNumber = new TimestampPkGenerator().next(getClass());
            channel.setChannel(channelNumber);

        }else{
            channel.setChannel(channelVo.getChannel());
        }

        String merchantName = merchantInfoDao.selectNameById(channelVo.getMerchantId());
        channel.setMerchantId(channelVo.getMerchantId());
        channel.setSystemName(merchantName);
        channel.setAppName(channelVo.getAppName());
        return channel;
    }

}
