package com.mbr.admin.common.aop;

import com.mbr.admin.common.annotation.SysLog;
import com.mbr.admin.common.utils.ControllerUtil;
import com.mbr.admin.manager.log.SysLogManager;
import com.mbr.admin.manager.security.SecurityUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;


@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static final String LOG_CONTENT = "[类名]:%s <br/>[方法]:%s <br>[参数]:%s <br/>[  IP ]:%s";

    @Autowired
    @Lazy
    private SysLogManager sysLogManager;

    @Around(value = "@annotation(com.mbr.admin.common.annotation.SysLog)")
    public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String methodName = joinPoint.getSignature().getName();
        Method method = currentMethod(joinPoint, methodName);
        SysLog log = method.getAnnotation(SysLog.class);
        SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        String principal = "";
        if(securityUserDetails==null) {
            principal = " - ";
        }else{
            principal = securityUserDetails.getUsername();
        }
        logger.info("@Syslog value : {} username:{}",log.value());
        if (log != null) {
            String content =buildeContent(joinPoint, methodName, request);
            sysLogManager.saveLog(content,new Date(),principal,log.value());
        }
        return joinPoint.proceed();
    }


    /**
     * 获取当前方法
     * @param joinPoint
     * @param methodName
     * @return
     */
    public  Method currentMethod(ProceedingJoinPoint joinPoint,String methodName){
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

    /**
     * 日志内容
     * @param joinPoint
     * @param methodName
     * @param request
     * @return
     */
    public String buildeContent(ProceedingJoinPoint joinPoint, String methodName, HttpServletRequest request) {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        StringBuffer bf = new StringBuffer();
        if (params != null && params.length > 0) {
            Enumeration<String> paraNames = request.getParameterNames();
            while (paraNames.hasMoreElements()) {
                String key = paraNames.nextElement();
                bf.append(key).append("=");
                bf.append(request.getParameter(key)).append("&");
            }
            if (StringUtils.isBlank(bf.toString())) {
                bf.append(request.getQueryString());
            }
        }
        logger.info("REQUEST PARAMS :"+bf.toString());
        return String.format(LOG_CONTENT, className, methodName, bf.toString(), ControllerUtil.getRemoteAddress(request));
    }

}