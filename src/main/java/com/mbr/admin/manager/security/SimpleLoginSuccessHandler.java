package com.mbr.admin.manager.security;

import com.mbr.admin.common.annotation.SysLog;
import com.mbr.admin.common.utils.NetworkUtils;
import com.mbr.admin.domain.system.SysUsers;
import com.mbr.admin.manager.system.SysUsersManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SimpleLoginSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(SimpleLoginSuccessHandler.class);

    @Autowired
    private SysUsersManager sysUsersManager;

    @Override
    @SysLog(value = "登录成功")
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) authentication.getPrincipal();
        logger.info(securityUserDetails.getUsername(),"{}->登录成功");
        SysUsers sysUsers = new SysUsers();
        sysUsers.setId(securityUserDetails.getId());
        sysUsers.setLastLogin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        sysUsers.setLoginIp(NetworkUtils.getIpAddr(httpServletRequest));
        try {
            sysUsersManager.updateByPrimaryKeySelective(sysUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpServletResponse.sendRedirect("/index");
    }
}
