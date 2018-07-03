package com.mbr.admin.manager.app;


import com.mbr.admin.domain.app.ProductApply;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface AuditProductManager {

    public Map<String,Object> queryList(String channelSearch, Pageable page);

    public ProductApply queryById(String id);

    public Object auditProductPass(ProductApply productApply);

    public Object auditProductFailed(String id);
}
