package com.mbr.admin.manager.log;

import com.mbr.admin.common.domain.vo.PageResult;
import com.mbr.admin.domain.log.SysLog;

import java.util.Date;
import java.util.List;

public interface SysLogManager {

    void saveLog(String content, Date createTime, String username, String type);

    PageResult<SysLog> queryList(SysLog sysLog, int pageSize, int pageNo);

}
