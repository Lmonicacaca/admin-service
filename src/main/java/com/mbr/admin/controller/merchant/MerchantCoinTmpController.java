package com.mbr.admin.controller.merchant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.merchant.MerchantCoinTmp;
import com.mbr.admin.manager.merchant.MerchantCoinTmpManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("merchantCoinTmp")
public class MerchantCoinTmpController extends BaseController {

    @Autowired
    private MerchantCoinTmpManager merchantCoinTmpManager;



    @RequestMapping("initPage")
    public String initPage(){
        return "merchant/merchantCoinTmpList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String merchantIdSearch){

        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<MerchantCoinTmp> coinTmpList = merchantCoinTmpManager.queryList(merchantIdSearch);
        PageResultDto result = new PageResultDto<MerchantCoinTmp>(new PageInfo<MerchantCoinTmp>(coinTmpList));
        return result;
    }
    @RequestMapping("queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return merchantCoinTmpManager.queryChannel();
    }

    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(String id){
        if(id==null){
            return failed("ID不能为空");
        }
        MerchantCoinTmp merchantCoinTmp = merchantCoinTmpManager.queryById(id);
        return success(merchantCoinTmp);
    }
    @RequestMapping("auditMerchant")
    @ResponseBody
    public Object auditMerchant(MerchantCoinTmp merchantCoinTmp){
        System.out.println(merchantCoinTmp);
        try {
            String result = merchantCoinTmpManager.auditMerchant(merchantCoinTmp);
            if("success".equals(result)){
                return success();
            }else{
                return failed();
            }
        }catch (MerchantException e){
            return failed(e.getMessage());
        }
    }
    @RequestMapping("auditFailed")
    @ResponseBody
    public Object auditFailed(String id){
        if(id==null){
            return failed("ID不能为空");
        }
        int i = merchantCoinTmpManager.auditMercahntNotPass(id, 2);
        if(i>0){
            return success();
        }else{
            return failed();
        }
    }
}
