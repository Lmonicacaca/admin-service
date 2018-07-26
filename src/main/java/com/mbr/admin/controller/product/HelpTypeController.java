package com.mbr.admin.controller.product;

import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.app.HelpType;
import com.mbr.admin.manager.app.HelpTypeManager;
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
@RequestMapping("helpType")
public class HelpTypeController extends BaseController {

    @Autowired
    private HelpTypeManager helpTypeManager;
    @RequestMapping("/initPage")
    public String initPage(){
        return "product/helpTypeList";
    }

    @RequestMapping("/queryList")
    @ResponseBody
    public Object queryList(Long idSearch, HttpServletRequest request){
        Pageable page = new PageRequest(super.getPageNo(request)-1, super.getPageSize(request));
        Map<String, Object> map = helpTypeManager.queryList(idSearch,page);
        PageResultDto result = result((List) map.get("list"), Long.valueOf(map.get("total").toString()));
        return result;
    }
    @RequestMapping("/addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(HelpType helpType){
        try{
            String result = helpTypeManager.addOrUpdate(helpType);
            if("success".equals(result)){
                return success();
            }
            return failed();
        }catch (MerchantException e){
            return failed(e.getMessage());
        }
    }
    @RequestMapping("/deleteHelpType")
    @ResponseBody
    public Object deleteHelpType(Long id){
        helpTypeManager.deleteHelpType(id);
        return success();
    }
    @RequestMapping("/queryById")
    @ResponseBody
    public Object queryById(Long id){
        Object helpType = helpTypeManager.queryById(id);
        return success(helpType);
    }
}
