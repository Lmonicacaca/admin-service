package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;
import com.mbr.admin.domain.merchant.Channel;

import java.util.List;

public interface PrivacyPolicyAndAboutManager {

    public List<PrivacyPolicyAndAbout> queryList(String channel);

    public Object queryChannel();

    public Object queryType();

    public int addOrUpdate(PrivacyPolicyAndAbout privacyPolicyAndAbout);

    public void deletePrivacyPolicyAndAbout(Long id);

    public PrivacyPolicyAndAbout queryById(Long id);
}
