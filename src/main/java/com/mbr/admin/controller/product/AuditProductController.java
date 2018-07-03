package com.mbr.admin.controller.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.app.ProductApply;
import com.mbr.admin.manager.app.AuditProductManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("auditProduct")
public class AuditProductController extends BaseController {

    @Value("${image_url}")
    private String image_url ;

    @Autowired
    private AuditProductManager auditProductManager;

    @RequestMapping("initPage")
    public String initPage(){
        return "product/auditProductList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String channelSearch){
        Pageable page = new PageRequest(super.getPageNo(request), super.getPageSize(request),new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));
        Map<String, Object> map = auditProductManager.queryList(channelSearch, page);
        PageResultDto result = result((List) map.get("list"), Long.valueOf(map.get("total").toString()));
        return result;
    }
    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(String id){
        if(id==null){
            return failed("ID不能为空");
        }
        ProductApply productApply = auditProductManager.queryById(id);
        if(productApply!=null){
            return success(productApply);
        }
        return failed();
    }

    @RequestMapping("auditProduct")
    @ResponseBody
    public Object auditProduct(ProductApply productApply){
        System.out.println(productApply);
        auditProductManager.auditProductPass(productApply);
        return success();
    }
    @RequestMapping("auditFailed")
    @ResponseBody
    public Object auditFailed(String id){
        System.out.println("$$$$$$$4"+id);
        if(id==null){
            return failed("ID不能为空");
        }
        Object object = auditProductManager.auditProductFailed(id);
        if(object!=null){
            return success();
        }
        return failed();
    }
}
