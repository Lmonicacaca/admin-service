package com.mbr.admin.controller.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.app.Vo.EthTransactionVo;
import com.mbr.admin.manager.app.EthTransactionManager;
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
    public Object queryList(HttpServletRequest request,String orderIdSearch, String fromSearch, String toSearch,String statusSearch){
        if(statusSearch.equals("999")){
            statusSearch = null;
        }
        Pageable page = new PageRequest(super.getPageNo(request)-1, super.getPageSize(request));
        Map<String, Object> map = ethTransactionManager.queryList(orderIdSearch, fromSearch, toSearch,statusSearch, page);
        return result((List) map.get("list"),Long.valueOf(map.get("total").toString()));
    }

}
