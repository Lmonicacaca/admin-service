package com.mbr.admin.controller.system;

import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.system.SysResources;
import com.mbr.admin.manager.security.SecurityUserDetails;
import com.mbr.admin.manager.system.SysResourcesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Ethan.Yuan on 2017/5/27.
 */
@Controller
@RequestMapping("resource")
public class SysResourcesController extends BaseController<SysResources> {

    private static Logger logger = LoggerFactory.getLogger(SysResourcesController.class);

    @Autowired
    SysResourcesManager sysResourcesManager;


    /**
     * 菜单用户所拥有的资源
     *
     * @param sysResources
     * @param request
     * @return object
     */
    @RequestMapping(value = "queryResources")
    @ResponseBody
    public Object queryResources(SysResources sysResources, HttpServletRequest request) {
        logger.info("菜单查询业务开始");
        //菜询菜单资源
        List<SysResources> sysResourcesList = sysResourcesManager.queryTree(sysResources);
        return sysResourcesList;
    }

    @RequestMapping(value = "resource")
    public Object queryResources(HttpServletRequest request){
        return "system/sysResourceList";
    }


    /**
     * 增加资源
     *
     * @param sysResource the sys resource vo
     * @param bindingResult the binding result
     * @param request       the request
     * @return object
     */
    @RequestMapping(value = "addOrUpdateResource", method = RequestMethod.POST)
    @ResponseBody
    public Object addOrUpdateResource(SysResources sysResource, BindingResult bindingResult, HttpServletRequest request) {
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();

        if(sysResource.getId()==null) {
            try {
                sysResource.setId(new TimestampPkGenerator().next(this.getClass()));
                sysResource.setEnable(1);
                sysResource.setCreateTime(new Date());
                sysResource.setCreateUserName(securityUserDetails.getUsername());
                sysResource.setStatus(0);
                sysResourcesManager.insert(sysResource);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return super.failed();
            } catch (Exception e) {
                e.printStackTrace();
                return super.failed("发生未知异常!");
            }
            return super.success();
        }else{
            return this.updateResource(sysResource,bindingResult,request);
        }
    }

    /**
     * 修改资源
     *
     * @param
     * @param bindingResult       the binding result
     * @param request             the request
     * @return object
     */
    @RequestMapping(value = "updateResource", method = RequestMethod.POST)
    @ResponseBody
    public Object updateResource(SysResources sysResource, BindingResult bindingResult, HttpServletRequest request) {
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        try {
            //入参校验
            if (sysResource.getId() == null) {
                throw new IllegalArgumentException();
            }
            sysResource.setUpdateTime(new Date());
            sysResource.setCreateUserName(securityUserDetails.getUsername());
            sysResource.setResourceParent(sysResource.getResourceParentUpdate());
            sysResourcesManager.updateByPrimaryKeySelective(sysResource);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return super.failed("没有记录的id号");
        } catch (Exception e) {
            e.printStackTrace();
            return super.failed("发生未知异常!");
        }
        return super.success();
    }
    @RequestMapping(value = "queryResourcesById")
    @ResponseBody
    public Object queryResourcesById(Long id, HttpServletRequest request) {
        //菜询菜单资源
        SysResources sysResources = sysResourcesManager.queryById(id);
        if(null == sysResources){
            return super.failed("没有指定的数据!");
        }else{
            SysResources sr = sysResourcesManager.queryById(sysResources.getResourceParent());
            if(sr!=null) {
                sysResources.setParentName(sr.getResourceName());
            }
            return super.success(sysResources);
        }
    }


    /**
     * 删除资源
     *
     * @param id      the id
     * @param request the request
     * @return object
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Long id, HttpServletRequest request) {
        try {
            if (id == null || id.equals("")) {
                throw new IllegalArgumentException();
            }
            SysResources sysResources = new SysResources();
            sysResources.setStatus(1);
            sysResources.setId(id);
            sysResourcesManager.updateByPrimaryKeySelective(sysResources);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return super.failed("没有记录的id号");
        } catch (Exception e) {
            e.printStackTrace();
            return super.failed("发生未知异常!");
        }
        return super.success();
    }

}
