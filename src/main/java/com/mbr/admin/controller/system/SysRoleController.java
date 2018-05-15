package com.mbr.admin.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.system.SysRoles;
import com.mbr.admin.domain.system.vo.SysTreeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.manager.security.SecurityUserDetails;
import com.mbr.admin.manager.system.SysRolesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "sysRole")
public class SysRoleController extends BaseController<SysRoles> {
    @Autowired
    private SysRolesManager sysRolesManager;

    @RequestMapping(value = "list",method = RequestMethod.POST)
    public String list(String menuId){
        return "system/sysRoleList";
    }


    @RequestMapping(value = "queryList", method = RequestMethod.POST)
    @ResponseBody
    public Object queryList(HttpServletRequest request, String roleName) {
        SysRoles sysRoles = new SysRoles();
        sysRoles.setRoleName(roleName);
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<SysRoles> list = sysRolesManager.queryByPage(sysRoles);
        PageResultDto result = new PageResultDto<>(new PageInfo<>(list));
        return result;
    }

    /**
     * @desc 新增角色
     * @author Ethan.Yuan
     * @param request
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(HttpServletRequest request, @ModelAttribute SysRoles sysRole, Long id) throws Exception{
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();

        if(id != null){
            return this.update(request,sysRole);
        }else {
            sysRole.setRoleAuth("ROLE_" + sysRole.getRoleAuth());
            sysRole.setCreateUserName(securityUserDetails.getUsername());
            sysRole.setCreateTime(new Date());
            sysRole.setEnable(true);
            sysRole.setStatus(0);
            sysRole.setId(new TimestampPkGenerator().next(getClass()));
            sysRolesManager.insert(sysRole);
            return super.success();
        }
    }

    /**
     * @desc 根据id获取详情
     * @author Ethan.Yuan
     * @param id
     * @return
     */
    @RequestMapping(value="getById")
    @ResponseBody
    public Object getById(Long id){
        SysRoles sysRole = new SysRoles();
        sysRole = sysRolesManager.queryById(id);
        sysRole.setRoleAuth(sysRole.getRoleAuth().replace("ROLE_", ""));
        return success(sysRole);
    }
    /**
     * @desc 修改角色
     * @author Ethan.Yuan
     * @param request
     * @param sysRole
     * @return
     * @throws Exception
     */
    public Object update(HttpServletRequest request,@ModelAttribute SysRoles sysRole) throws Exception{
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        sysRole.setRoleAuth("ROLE_"+sysRole.getRoleAuth());
        sysRole.setUpdateUserName(securityUserDetails.getUsername());
        sysRole.setUpdateTime(new Date());
        sysRolesManager.updateByPrimaryKeySelective(sysRole);
        return super.success();
    }
    /**
     * @desc 逻辑删除
     * @author Ethan.Yuan
     * @param id
     * @return
     */
    @RequestMapping(value="delete")
    @ResponseBody
    public Object delete(Long id){
        SysRoles sysRoles = new SysRoles();
        sysRoles.setId(id);
        sysRoles.setStatus(1);
        try {
            sysRolesManager.updateByPrimaryKeySelective(sysRoles);
            return super.success();
        } catch (Exception e) {
            e.printStackTrace();
            return super.failed(e.getMessage());
        }
    }
    /**
     * @desc 逻辑删除
     * @author Ethan.Yuan
     * @param id
     * @return
     */
    @RequestMapping(value="getTree",method = RequestMethod.GET)
    @ResponseBody
    public Object getTree(Long id){
        List<SysTreeVo> list = sysRolesManager.getTree(id);
        return list;
    }

    /**
     * @desc 逻辑删除
     * @author Ethan.Yuan
     * @param id
     * @return
     */
    @RequestMapping(value="addRoleReSource",method = RequestMethod.POST)
    @ResponseBody
    public Object addRoleReSource(Long id,String resourceId){
        try {
            JSONArray jsonArray = JSONArray.parseArray(resourceId);
            Long[] ids = new Long[jsonArray.size()];
            for(int i=0;i<jsonArray.size();i++){
                ids[i]= Long.parseLong((String)jsonArray.get(i));
            }
            sysRolesManager.addReSource(id,ids);
            return  success();
        } catch (Exception e) {
            e.printStackTrace();
            return failed(e.getMessage());
        }

    }
}
