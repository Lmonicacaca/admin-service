package com.mbr.admin.manager.app;

import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.app.HelpType;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface HelpTypeManager {
    public Map<String, Object> queryList(Long id, Pageable page);

    public String addOrUpdate(HelpType helpType)throws MerchantException;

    public void deleteHelpType(Long id);

    public Object queryById(Long id);
}
