package com.mbr.admin.manager.system;

import com.mbr.admin.common.manager.BaseManager;
import com.mbr.admin.domain.system.SysUsers;
import com.mbr.admin.domain.system.vo.SysUsersVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysUsersManager extends BaseManager<SysUsers> {

    public SysUsersVo loadByUsername(String username);

    public List<SysUsers> queryListByPage(SysUsers sysUsers);

    @Transactional(rollbackFor=Exception.class)
    public void insertUser(SysUsers sysUsers)throws Exception;

    @Transactional(rollbackFor=Exception.class)
    public void update(SysUsers sysUsers, Long roleId) throws Exception;

}
