package com.mbr.admin.controller.merchant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.merchant.PayBill;
import com.mbr.admin.manager.merchant.PayBillManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("payBill")
public class PayBillController extends BaseController {

    @Autowired
    private PayBillManager payManager;
    @RequestMapping("initPage")
    public String initPage(){
        return "merchant/paybillList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String merchantidSearch, String fromAddrSearch, String toAddrSearch,String billTypeSearch,String merchantnameSearch,String statusSearch){
        int billType = Integer.parseInt(billTypeSearch);
        int status = Integer.parseInt(statusSearch);
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<PayBill> payBillList = payManager.queryAllPayBill(merchantidSearch,fromAddrSearch,toAddrSearch,billType,merchantnameSearch,status);
        PageResultDto result = new PageResultDto<PayBill>(new PageInfo<PayBill>(payBillList));
        return result;
    }



}
