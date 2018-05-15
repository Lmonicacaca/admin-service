package com.mbr.admin.manager.system.impl;

import com.mbr.admin.common.manager.impl.BaseManagerImpl;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.dao.system.SysRolesDao;
import com.mbr.admin.domain.system.SysResources;
import com.mbr.admin.domain.system.SysRoles;
import com.mbr.admin.domain.system.SysRolesResources;
import com.mbr.admin.domain.system.vo.SysTreeVo;
import com.mbr.admin.manager.security.SecurityUserDetails;
import com.mbr.admin.manager.system.SysResourcesManager;
import com.mbr.admin.manager.system.SysRolesManager;
import com.mbr.admin.manager.system.SysRolesResourcesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("sysRolesManager")
public class SysRolesManagerImpl extends BaseManagerImpl<SysRoles> implements SysRolesManager {

    @Autowired
    private SysRolesDao sysRolesDao;

    @Autowired
    private SysResourcesManager sysResourcesManager;

    @Autowired
    private SysRolesResourcesManager sysRolesResourcesManager;

    @Override
    public List<SysRoles> select2(SysRoles sysRoles) {
        return sysRolesDao.select2(sysRoles);
    }

    @Override
    public List<SysRoles> queryByPage(SysRoles sysRoles) {
        return sysRolesDao.queryByPage(sysRoles);
    }

    @Override
    public List<SysTreeVo> getTree(Long id) {
        List<SysTreeVo> treeVoList = new ArrayList<>();
        SysResources sr = new SysResources();
        sr.setStatus(0);
        List<SysResources> resourcesManagerList = sysResourcesManager.queryAll(sr);
        SysRolesResources sysRolesResources = new SysRolesResources();
        sysRolesResources.setRoleId(id);
        sysRolesResources.setStatus(0);
        List<SysRolesResources> list = sysRolesResourcesManager.queryAll(sysRolesResources);
        //先找所以的一级菜单
        for (SysResources sysResources : resourcesManagerList) {
            if (sysResources.getResourceParent()==0) {
                SysTreeVo sysTreeVo = new SysTreeVo();
                sysTreeVo.setText(sysResources.getResourceName());
                sysTreeVo.setId(sysResources.getId());
                for(SysRolesResources rolesResources:list) {
                    Map<String, Boolean> map = new HashMap<>();
                    if(String.valueOf(rolesResources.getResourceId()).equals(String.valueOf( sysResources.getId()))) {
                        map.put("selected", true);
                        sysTreeVo.setState(map);
                        break;
                    }else{
                        map.put("selected", false);
                        sysTreeVo.setState(map);
                    }
                }
                sysTreeVo.setChildren(getChild(sysResources.getId(), resourcesManagerList,list));
                treeVoList.add(sysTreeVo);
            }
        }

        return treeVoList;
    }

    @Override
    public void addReSource(Long roleId, Long[] reSource) throws Exception {
        SysRolesResources sysRolesResources = new SysRolesResources();
        sysRolesResources.setRoleId(roleId);
        sysRolesResourcesManager.delete(sysRolesResources);
        for(Long reSourceId:reSource){
            SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            SysRolesResources sr = new SysRolesResources();
            sr.setRoleId(roleId);
            sr.setResourceId(reSourceId);
            sr.setId(new TimestampPkGenerator().next(getClass()));
            sr.setCreateTime(new Date());
            sr.setCreateUserName(securityUserDetails.getUsername());
            sr.setStatus(0);
            sysRolesResourcesManager.insertSelective(sr);
        }
    }

    /**
     * 递归查找子菜单
     *
     * @param id
     *            当前菜单id
     * @param rootMenu
     *            要查找的列表
     * @return
     */
    private List<SysTreeVo> getChild(Long id, List<SysResources> rootMenu,List<SysRolesResources> list) {
        // 子菜单
        List<SysTreeVo> childList = new ArrayList<>();
        for (SysResources menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getResourceParent()!=null) {
                if (String.valueOf(menu.getResourceParent()).equals(String.valueOf(id))) {
                    SysTreeVo sysTreeVo = new SysTreeVo();
                    sysTreeVo.setText(menu.getResourceName());
                    sysTreeVo.setId(menu.getId());
                    for(SysRolesResources rolesResources:list) {
                        Map<String, Boolean> map = new HashMap<>();
                        if(String.valueOf(rolesResources.getResourceId()).equals(String.valueOf( menu.getId()))) {
                            map.put("selected", true);
                            sysTreeVo.setState(map);
                            break;
                        }else{
                            map.put("selected", false);
                            sysTreeVo.setState(map);
                        }
                    }
                    sysTreeVo.setChildren(getChild(menu.getId(), rootMenu,list));
                    childList.add(sysTreeVo);
                }
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}
