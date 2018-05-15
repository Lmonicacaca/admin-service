package com.mbr.admin.manager.system.impl;

import com.mbr.admin.common.manager.impl.BaseManagerImpl;
import com.mbr.admin.dao.system.SysResourcesDao;
import com.mbr.admin.domain.system.SysResources;
import com.mbr.admin.manager.system.SysResourcesManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysResourcesManagerImpl extends BaseManagerImpl<SysResources> implements SysResourcesManager {

    @Resource
    private SysResourcesDao sysResourcesDao;

    @Override
    public List<SysResources> queryTree(SysResources sys) {
        List<SysResources> listMenu = new ArrayList<>();
        List<SysResources> list = null;
        try {
            list = this.queryAllResource(sys);
        } catch (Exception e) {
            e.printStackTrace();
            return listMenu;
        }
        //先找所以的一级菜单
        for (SysResources sysResources : list) {
            if (sysResources.getResourceParent()==0) {
                sysResources.setTitle(sysResources.getResourceName());
                listMenu.add(sysResources);
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysResources menu : listMenu) {
            menu.setTitle(menu.getResourceName());
            menu.setExpanded(true);
            //判定是否有字节点
            for(SysResources s:listMenu){
                if(String.valueOf( menu.getId()).equals(String.valueOf(s.getResourceParent()))){
                    menu.setFolder(true);
                    break;
                }
            }
            menu.setChildren(getChild(menu.getId(), list));
        }
        return listMenu;
    }

    @Override
    public List<SysResources> queryMenu(Long userId) {
        List<SysResources> listMenu = new ArrayList<>();
        List<SysResources> list = queryAllByUserId(userId);
        //先找所以的一级菜单
        for(SysResources sysResources:list){
            if(sysResources.getResourceParent()==0){
                listMenu.add(sysResources);
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysResources menu : listMenu) {
            menu.setChildren(getChild(menu.getId(), list));
        }
        return listMenu;
    }

    @Override
    public List<SysResources> queryAllByUserId(Long userId) {
        return sysResourcesDao.queryAllByUserId(userId);
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
    private List<SysResources> getChild(Long id, List<SysResources> rootMenu) {
        // 子菜单
        List<SysResources> childList = new ArrayList<>();
        for (SysResources menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getResourceParent()!=null) {
                if (String.valueOf(id).equals(String.valueOf(menu.getResourceParent()))) {
                    menu.setTitle(menu.getResourceName());
                    menu.setExpanded(true);
                   // for(SysResources s:rootMenu) {
                        //if (menu.getId()==s.getResourceParent()) {
                            menu.setFolder(true);
                       // }
                   // }
                    childList.add(menu);
                    menu.setChildren(getChild(menu.getId(), rootMenu));
                }
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    public List<SysResources> queryAllResource(SysResources sysResources) {
        return sysResourcesDao.queryAllResource(sysResources);
    }
}
