package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.Channel;

import java.util.List;
import java.util.Map;

public interface ChannelManager {
    public List<Map<String,Object>> findAllChannel();

    public Channel insertChannel(Channel channel);
}
