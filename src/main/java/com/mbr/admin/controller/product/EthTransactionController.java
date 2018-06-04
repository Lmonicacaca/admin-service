package com.mbr.admin.controller.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.manager.app.EthTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("ethTransaction")
public class EthTransactionController extends BaseController {

    @Autowired
    private EthTransactionManager ethTransactionManager;
    @RequestMapping("initPage")
    public String initPage(){
        return "product/ethTransactionList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String orderIdSearch, String fromSearch, String toSearch){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<Map<String,Object>> list = ethTransactionManager.queryList(orderIdSearch,fromSearch,toSearch);
        PageResultDto result = new PageResultDto<Map<String,Object>>(new PageInfo<Map<String,Object>>(list));

        return result;
    }

}
