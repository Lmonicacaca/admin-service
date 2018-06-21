package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChannelManagerImpl implements ChannelManager {

    @Resource
    private ChannelRepository channelRepository;

    @Override
    public List<Map<String, Object>> findAllChannel() {
        List<Channel> channelList = channelRepository.findAllByStatus(0);
        List<Channel> channelListDistinct = new ArrayList<>();
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i =0;i<channelList.size();i++){
            if(i==0){
                channelListDistinct.add(channelList.get(i));
            }else{
                if(!channelList.get(i).getChannel().equals(channelListDistinct.get(channelListDistinct.size()-1).getChannel())){
                    channelListDistinct.add(channelList.get(i));
                }
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
}
