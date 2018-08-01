package com.mbr.admin.repository;

import com.mbr.admin.domain.merchant.Channel;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.event.ChangeEvent;
import java.util.List;

public interface ChannelRepository extends MongoRepository<Channel,Long>{

    public List<Channel> findAllByStatus(int status);

//    public List<Channel> fin();

    public Channel findByMerchantIdAndChannelAndAppName(String merchantId,Long channel,String appName);

    public List<Channel> findByChannelAndMerchantId(Long channel,String merchantId);



}
