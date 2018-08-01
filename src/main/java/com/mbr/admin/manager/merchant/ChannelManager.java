package com.mbr.admin.manager.merchant;

import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.merchant.Channel;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.Vo.ChannelVo;
import com.mbr.admin.domain.merchant.Vo.MerchantInfoVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ChannelManager {
    public List<Map<String,Object>> findAllChannel();//查询去重的所有的channel

    public Channel insertChannel(Channel channel);

    public Map<String,Object> queryAllChannel(String merchantName, String appName, Pageable page);//查询所有的channel


    public List<Map<String,Object>> queryMerchantId();

    public String addOrUpdate(ChannelVo channelVo)throws MerchantException;

    public List<Channel> queryChannelByMerchantIdAndChannel(Long channel,Long merchantId);

    public int updateChannel(List<Channel> channelList, MerchantInfoVo merchantInfoVo);

    public void deleteChannel(Long id);

    public Channel queryById(Long id);
}
