package com.mbr.admin.controller.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.app.Help;
import com.mbr.admin.manager.app.HelpManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("help")
public class HelpController extends BaseController {

    @Autowired
    private HelpManager helpManager;

    @RequestMapping("initPage")
    public String initPage(){
        return "product/helpList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String titleSearch){
        Pageable page = new PageRequest(super.getPageNo(request), super.getPageSize(request),new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));
        Map<String, Object> map = helpManager.queryList(titleSearch, page);
        PageResultDto result = result((List) map.get("list"), Long.valueOf(map.get("total").toString()));
        return result;
    }
    @RequestMapping("addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(Help help){
        System.out.println(help);
        int i = helpManager.addOrUpdate(help);
        if(i>0){
            return success();
        }
        return failed();
    }
    @RequestMapping("deleteHelp")
    @ResponseBody
    public Object deleteHelp(Long id){
        helpManager.deleteHelp(id);
        return success();
    }

    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(Long id){
        if(id==null){
            return failed("ID不能为空");
        }
        Help help = helpManager.queryById(id);
        if(help!=null){
            return success(help);
        }
        return failed();
    }
}
