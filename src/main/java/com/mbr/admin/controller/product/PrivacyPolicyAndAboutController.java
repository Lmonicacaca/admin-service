package com.mbr.admin.controller.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;
import com.mbr.admin.manager.app.PrivacyPolicyAndAboutManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("privacyPolicyAndAbout")
public class PrivacyPolicyAndAboutController extends BaseController {

    @Autowired
    private PrivacyPolicyAndAboutManager privacyPolicyAndAboutManager;

    @RequestMapping(value="initPage",method = RequestMethod.POST)
    public String initPage(){
        return "product/privacyPolicyAndAboutList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String channelSearch){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<PrivacyPolicyAndAbout> privacyPolicyAndAboutList = privacyPolicyAndAboutManager.queryList(channelSearch);//查询所有状态码为0的信息
        PageResultDto result = new PageResultDto<PrivacyPolicyAndAbout>(new PageInfo<PrivacyPolicyAndAbout>(privacyPolicyAndAboutList));
        return result;
    }

    @RequestMapping("queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return privacyPolicyAndAboutManager.queryChannel();
    }
    @RequestMapping("queryType")
    @ResponseBody
    public Object queryType(){
        return privacyPolicyAndAboutManager.queryType();

    }

    @RequestMapping("addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(PrivacyPolicyAndAbout privacyPolicyAndAbout){
        System.out.println(privacyPolicyAndAbout);
        int i = privacyPolicyAndAboutManager.addOrUpdate(privacyPolicyAndAbout);
        if(i>0){
            return success();
        }
        return failed();
    }
    @RequestMapping("deletePrivacyPolicyAndAbout")
    @ResponseBody
    public Object deletePrivacyPolicyAndAbout(Long id){
        privacyPolicyAndAboutManager.deletePrivacyPolicyAndAbout(id);
        return success();
    }
    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(Long id){
        if(id==null){
            return failed("ID不能为空");
        }
        PrivacyPolicyAndAbout privacyPolicyAndAbout = privacyPolicyAndAboutManager.queryById(id);
        if(privacyPolicyAndAbout!=null){
            return success(privacyPolicyAndAbout);
        }
        return failed();
    }
}
