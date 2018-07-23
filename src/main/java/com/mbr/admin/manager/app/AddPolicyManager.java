package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;

import java.util.List;
import java.util.Map;

public interface AddPolicyManager {

    public List<Map<String,Object>> queryChannel();

    public int addPolicy(PrivacyPolicyAndAbout privacyPolicyAndAbout);
}
