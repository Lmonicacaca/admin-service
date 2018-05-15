package com.mbr.admin.manager.system;

import com.mbr.admin.common.manager.BaseManager;
import com.mbr.admin.domain.system.SysRoles;
import com.mbr.admin.domain.system.vo.SysTreeVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysRolesManager extends BaseManager<SysRoles> {

    public List<SysRoles> select2(SysRoles sysRoles);

    public List<SysRoles> queryByPage(SysRoles sysRoles);

    public List<SysTreeVo> getTree(Long id);
    @Transactional(rollbackFor=Exception.class)
    public void addReSource(Long roleId, Long[] reSource) throws Exception;

}
