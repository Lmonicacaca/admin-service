package com.mbr.admin.manager.system;


import com.mbr.admin.common.manager.BaseManager;
import com.mbr.admin.domain.system.SysResources;

import java.util.List;

public interface SysResourcesManager extends BaseManager<SysResources> {

    public List<SysResources> queryTree(SysResources sys);


    public List<SysResources> queryMenu(Long userId);


    public List<SysResources> queryAllByUserId(Long userId);
}
