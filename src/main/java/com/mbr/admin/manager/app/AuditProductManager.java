package com.mbr.admin.manager.app;


import com.mbr.admin.domain.app.ProductApply;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuditProductManager {

    public List<ProductApply> queryList(String channelSearch);

    public ProductApply queryById(String id);

    public Object auditProductPass(ProductApply productApply);

    public Object auditProductFailed(String id);
}
