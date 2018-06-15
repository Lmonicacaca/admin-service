package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;
import com.mbr.admin.domain.merchant.Channel;

import java.util.List;

public interface PrivacyPolicyAndAboutManager {

    public List<PrivacyPolicyAndAbout> queryList(String channel);

    public List<Channel> queryChannel();

    public void deletePrivacyPolicyAndAbout(Long id);

    public PrivacyPolicyAndAbout queryById(Long id);

    public void updateById(PrivacyPolicyAndAbout privacyPolicyAndAbout);
}
