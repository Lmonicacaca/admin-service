package com.mbr.admin.controller.system;

import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.system.SysRoles;
import com.mbr.admin.domain.system.SysUsers;
import com.mbr.admin.domain.system.SysUsersRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.manager.security.SecurityUserDetails;
import com.mbr.admin.manager.system.SysRolesManager;
import com.mbr.admin.manager.system.SysUsersManager;
import com.mbr.admin.manager.system.SysUsersRoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("sysUser")
public class SysUsersController extends BaseController<SysUsers> {

    @Autowired
    private SysUsersManager sysUsersManager;

    @Autowired
    private SysRolesManager sysRolesManager;
    @Autowired
    private SysUsersRoleManager sysUsersRoleManager;

    @RequestMapping(value = "list",method = RequestMethod.POST)
    public String list(String menuId){
       return "system/sysUserList";

    }

    @RequestMapping(value = "queryList", method = RequestMethod.POST)
    @ResponseBody
    public Object queryList(HttpServletRequest request, String username, String name, String menuId) {
        SysUsers sysUsers = new SysUsers();
        sysUsers.setUsername(username);
        sysUsers.setName(name);
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<SysUsers> list = sysUsersManager.queryListByPage(sysUsers);
        PageResultDto result = new PageResultDto<SysUsers>(new PageInfo<SysUsers>(list));
        return result;
    }

    @RequestMapping(value = "querySysRole", method = RequestMethod.GET)
    @ResponseBody
    public  List<Map<String,Object>> querySysRole(String q){
        List<Map<String,Object>> list = new ArrayList<>();
        SysRoles sysRoles = new SysRoles();
        sysRoles.setRoleName(q);
        for(SysRoles sysRole:sysRolesManager.select2(sysRoles)){
            Map<String,Object> map = new HashMap<>();
            map.put("id",sysRole.getId());
            map.put("text",sysRole.getRoleName());
            list.add(map);
        }
        return list;
    }


    private Object updateById(Long id,String name,String username,Long roleId){
        SysUsers sysUser = sysUsersManager.queryById(id);
        sysUser.setId(id);
        sysUser.setName(name);
        sysUser.setUsername(username);
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUserName(sysUser.getName());
        try {
            sysUsersManager.update(sysUser,roleId);
            return super.success();
        } catch (Exception e) {
            e.printStackTrace();
            return super.failed(e.getMessage());
        }
    }

    @RequestMapping(value = "addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Object addOrUpdate(Long id,String username,String name,String password,Long roleId){
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();

        if(id != null){
            return updateById(id,name,username,roleId);
        }else {
            SysUsers query = new SysUsers();
            query.setUsername(username);
            query.setStatus(0);
            SysUsers s = sysUsersManager.queryOne(query);
            if (s == null) {
                SysUsers sysUser = new SysUsers();
                sysUser.setName(name);
                sysUser.setRoleId(roleId);
                sysUser.setId(new TimestampPkGenerator().next(getClass()));
                sysUser.setUsername(username);
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                sysUser.setPassword(bCryptPasswordEncoder.encode(password));
                sysUser.setAccountNonLocked(true);
                sysUser.setCredentialsNonExpired(true);
                sysUser.setAccountNonExpired(true);
                sysUser.setEnabled(true);
                sysUser.setCreateTime(new Date());
                sysUser.setCreateUserName(securityUserDetails.getUsername());
                sysUser.setStatus(0);
                try {
                    sysUsersManager.insertUser(sysUser);
                    return success();
                } catch (Exception e) {
                    e.printStackTrace();
                    return failed(e.getMessage());
                }
            } else {
                return failed("该用户名已存在!");
            }
        }
    }

    @RequestMapping(value = "queryById", method = RequestMethod.POST)
    @ResponseBody
    public Object queryById(Long id) {
        if (id == null) {
            return super.failed("用户ID 为空！");
        } else {
            SysUsers sysUser = sysUsersManager.queryById(id);
            SysUsersRole s = new SysUsersRole();
            s.setUserId(id);
            List<SysUsersRole> sysUserRole= sysUsersRoleManager.queryAll(s);
            //查询角色
            Map<String, Object> map = new HashMap<>();
            if(sysUserRole!=null&&sysUserRole.size()>0) {
                SysRoles role = sysRolesManager.queryById(sysUserRole.get(0).getRoleId());
                map.put("user", sysUser);
                map.put("role", role);
            }
            return super.success(map);
        }
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteUser(Long id){
        if (id ==null) {
            return super.failed("用户ID 为空！");
        }
        try {
            SysUsersRole sysUsersRoles = new SysUsersRole();
            sysUsersRoles.setUserId(id);
            sysUsersRoles = sysUsersRoleManager.queryOne(sysUsersRoles);
            sysUsersRoles.setStatus(1);
            sysUsersRoleManager.updateByPrimaryKeySelective(sysUsersRoles);
            SysUsers sysUsers = new SysUsers();
            sysUsers.setStatus(1);
            sysUsers.setId(id);
            sysUsersManager.updateByPrimaryKeySelective(sysUsers);
            return super.success();
        } catch (Exception e) {
            e.printStackTrace();
            return super.failed(e.getMessage());
        }
    }

    @RequestMapping(value = "resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public Object resetPwd(Long id){
        if (id == null) {
            return super.failed("用户ID 为空！");
        }
        SysUsers sysUser = new SysUsers();
        sysUser.setId(id);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        sysUser.setPassword(bCryptPasswordEncoder.encode("666666"));
        try {
            sysUsersManager.updateByPrimaryKeySelective(sysUser);
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return failed(e.getMessage());
        }
    }
}
