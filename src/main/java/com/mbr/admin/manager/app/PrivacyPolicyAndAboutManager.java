package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;
import com.mbr.admin.domain.merchant.Channel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PrivacyPolicyAndAboutManager {

    public Map<String,Object> queryList(String channel, Pageable page);

    public Object queryChannel();

    public Object queryType();

    public int addOrUpdate(PrivacyPolicyAndAbout privacyPolicyAndAbout);

    public void deletePrivacyPolicyAndAbout(Long id);

    public PrivacyPolicyAndAbout queryById(Long id);

    public String queryContent(Long id);
}
