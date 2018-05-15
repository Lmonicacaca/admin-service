package com.mbr.admin.controller.system;

import com.mbr.admin.common.annotation.SysLog;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.domain.system.SysResources;
import com.mbr.admin.domain.system.SysUsers;
import com.mbr.admin.manager.security.SecurityUserDetails;
import com.mbr.admin.manager.system.SysResourcesManager;
import com.mbr.admin.manager.system.SysUsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController extends BaseController {

    @Autowired
    private SysResourcesManager sysResourcesManager;
    @Autowired
    private SysUsersManager sysUsersManager;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Principal principal, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken securityUserDetails =(UsernamePasswordAuthenticationToken) principal;
        SecurityUserDetails sud = (SecurityUserDetails)securityUserDetails.getPrincipal();
        request.setAttribute("userDetails",sud);
        List<SysResources> list = sysResourcesManager.queryMenu(sud.getId());
        request.setAttribute("menuList", list);

        return "index";
    }
    @RequestMapping(value = "/pass/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changePassword(String oldPassword, String newPassword) {
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        SysUsers sysUsers = sysUsersManager.queryById(securityUserDetails.getId());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(oldPassword,sysUsers.getPassword())){
            SysUsers su = new SysUsers();
            su.setId(sysUsers.getId());
            su.setPassword(bCryptPasswordEncoder.encode(newPassword));
            try {
                sysUsersManager.updateByPrimaryKeySelective(su);
                return super.success();
            } catch (Exception e) {
                e.printStackTrace();
                return super.failed(e.getMessage());
            }
        }
        return super.failed("旧密码不正确");
    }
}
