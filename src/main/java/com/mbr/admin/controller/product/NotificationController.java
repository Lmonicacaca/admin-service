package com.mbr.admin.controller.product;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.Notification;
import com.mbr.admin.manager.app.NotificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("notification")
public class NotificationController extends BaseController {

    @Autowired
    private NotificationManager notificationManager;

    @RequestMapping(value = "initPage",method = RequestMethod.POST)
    public String initPage(){

        return "product/notificationList";
    }

    @RequestMapping(value = "queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,int typeSearch,int dealSearch){
        Pageable page = new PageRequest(super.getPageNo(request)-1, super.getPageSize(request));
        Map<String, Object> map = notificationManager.queryList(typeSearch, dealSearch, page);
        PageResultDto result = result((List) map.get("list"), Long.valueOf(map.get("total").toString()));
        return result;
    }

    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(Long id){
        if(id == null){
            return failed("ID 为空");
        }
        Notification notification = notificationManager.queryById(id);

        return success(notification);
    }

    @RequestMapping("addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(Notification notification){
        if(notification.getId()!=null){
            notificationManager.updateById(notification.getId(),notification.getTitle());
            return success();
        }else{
            notification.setId(new TimestampPkGenerator().next(getClass()));
            notification.setCreateTime(new Date());
            notificationManager.insertNotification(notification);
            return success();
        }
    }

    @RequestMapping("queryChannel")
    @ResponseBody
    public List<Map<String,Object>> queryChannel(){

        return notificationManager.queryChannel();
    }

    @RequestMapping("queryType")
    @ResponseBody
    public List<Map<String,Object>> queryType(){

        return notificationManager.queryType();
    }

    @RequestMapping("queryTransfer")
    @ResponseBody
    public List<Map<String,Object>> queryTransfer(){

        return notificationManager.queryTransfer();
    }



}
