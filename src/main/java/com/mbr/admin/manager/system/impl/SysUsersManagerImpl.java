package com.mbr.admin.manager.system.impl;

import com.mbr.admin.common.manager.impl.BaseManagerImpl;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.system.SysUsersDao;
import com.mbr.admin.domain.system.SysUsers;
import com.mbr.admin.domain.system.SysUsersRole;
import com.mbr.admin.domain.system.vo.SysUsersVo;
import com.mbr.admin.manager.system.SysUsersManager;
import com.mbr.admin.manager.system.SysUsersRoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("sysUsersManager")
public class SysUsersManagerImpl extends BaseManagerImpl<SysUsers> implements SysUsersManager {

    @Resource
    private SysUsersDao sysUsersDao;

    @Autowired
    private SysUsersRoleManager sysUsersRoleManager;

    @Override
    public SysUsersVo loadByUsername(String username) {
        return sysUsersDao.loadByUsername(username);
    }

    @Override
    public List<SysUsers> queryListByPage(SysUsers sysUsers) {
        return sysUsersDao.queryListByPage(sysUsers);
    }

    @Override
    public void insertUser(SysUsers sysUsers) throws Exception {
        try {
            sysUsersDao.insert(sysUsers);
            Long userId = sysUsers.getId();
            SysUsersRole sysUserRole = new SysUsersRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(sysUsers.getRoleId());
            sysUserRole.setCreateTime(new Date());
            sysUserRole.setCreateUserName(sysUsers.getCreateUserName());
            sysUserRole.setStatus(0);
            sysUserRole.setId(new TimestampPkGenerator().next(getClass()));
            sysUsersRoleManager.insert(sysUserRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(SysUsers sysUsers,Long roleId) throws Exception {
        sysUsersDao.updateByPrimaryKeySelective(sysUsers);
        SysUsersRole sysUsersRole = new SysUsersRole();
        sysUsersRole.setUserId(sysUsers.getId());
       // sysUsersRole.setRoleId(roleId);
        sysUsersRoleManager.delete(sysUsersRole);
        if(roleId!=null) {
            SysUsersRole sysUserRole = new SysUsersRole();
            sysUserRole.setId(new TimestampPkGenerator().next(getClass()));
            sysUserRole.setUserId(sysUsers.getId());
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUpdateTime(new Date());
            sysUserRole.setStatus(0);
            sysUserRole.setUpdateUserName(sysUsers.getName());
            sysUsersRoleManager.insert(sysUserRole);
        }
    }
}
