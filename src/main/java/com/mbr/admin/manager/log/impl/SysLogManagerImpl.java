package com.mbr.admin.manager.log.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mbr.admin.common.domain.vo.PageResult;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.log.SysLogDao;
import com.mbr.admin.domain.log.SysLog;
import com.mbr.admin.manager.log.SysLogManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("logManager")
public class SysLogManagerImpl implements SysLogManager {

    @Resource
    private SysLogDao sysLogDao;

    @Override
    public void saveLog(String content, Date createTime, String username, String type) {
        SysLog sysLog = new SysLog();
        sysLog.setId(new TimestampPkGenerator().next(getClass()));
        sysLog.setContent(content);
        sysLog.setCreateTime(createTime);
        sysLog.setUsername(username);
        sysLog.setType(type);
        sysLog.setStatus(0);
        sysLogDao.insertSelective(sysLog);
    }

    @Override
    public PageResult<SysLog> queryList(SysLog sysLog, int pageSize, int pageNo) {
        Page page = PageHelper.startPage(pageNo,pageSize);
        List<SysLog> logList = sysLogDao.select(sysLog);
        PageResult<SysLog> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setList(logList);
        return pageResult;
    }
}
