package com.mbr.admin.manager.app.impl;

import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;
import com.mbr.admin.manager.app.AddPolicyManager;
import com.mbr.admin.manager.merchant.ChannelManager;
import com.mbr.admin.repository.PrivacyPolicyAndAboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class AddPlolicyManagerImpl implements AddPolicyManager {

    @Autowired
    private ChannelManager channelManager;

    @Autowired
    private PrivacyPolicyAndAboutRepository privacyPolicyAndAboutRepository;
    @Override
    public List<Map<String, Object>> queryChannel() {
        return channelManager.findAllChannel();
    }

    @Override
    public int addPolicy(PrivacyPolicyAndAbout privacyPolicyAndAbout) {
        System.out.println(privacyPolicyAndAbout);
        if(privacyPolicyAndAbout.getId()==null){
            Long id = new TimestampPkGenerator().next(getClass());
            privacyPolicyAndAbout.setId(id);
            privacyPolicyAndAbout.setCreateTime(new Date());
            PrivacyPolicyAndAbout privacyPolicyAndAboutSave = privacyPolicyAndAboutRepository.save(privacyPolicyAndAbout);
            if(privacyPolicyAndAboutSave!=null){
                return 1;
            }
        }
        return 0;
    }
}
