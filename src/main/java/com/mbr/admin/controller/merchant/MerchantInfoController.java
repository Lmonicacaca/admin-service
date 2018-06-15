package com.mbr.admin.controller.merchant;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.manager.merchant.MerchantInfoManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("merchantInfo")
public class MerchantInfoController extends BaseController<MerchantInfo> {

    @Resource
    private MerchantInfoManager merchantInfoManager;

    @RequestMapping("initPage")
    public String initPage(){
        return "merchant/merchantInfoList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String nameSearch){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<MerchantInfo> payBillList = merchantInfoManager.queryList(nameSearch);
        PageResultDto result = new PageResultDto<MerchantInfo>(new PageInfo<MerchantInfo>(payBillList));
        return result;
    }

    @RequestMapping("queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return merchantInfoManager.queryChannel();
    }

    //审核商户，为商户设置渠道号
    @RequestMapping("auditMerchant")
    @ResponseBody
    public Object auditMerchant(MerchantInfo merchantInfo){
        System.out.println(merchantInfo);
        //判断商户是否填写渠道号
        if(merchantInfo.getChannel()==null||merchantInfo.getChannel()==""){
            try{
                String result = merchantInfoManager.saveChannelForMerchant(merchantInfo);
                return success();
            }catch (Exception e){
                return failed(e.getMessage());
            }

        }
        else{
            try{
                String result = merchantInfoManager.updateChannelForMerchant(merchantInfo);
                return success();
            }catch (Exception e){
                return failed(e.getMessage());
            }

        }
    }
    @RequestMapping("deleteMerchant")
    @ResponseBody
    public Object deleteMerchant(String id){

        if(id==null){
            return failed("ID不能为空");
        }
        int result = merchantInfoManager.deleteMerchantInfo(id);
        if(result>0){
            return success();
        }else{
            return failed();
        }
    }
    @RequestMapping("queryRsaPublic")
    @ResponseBody
    public String queryRsaPublic(String id){
        return merchantInfoManager.queryRsaPublic(id);
    }

    @RequestMapping("queryRsaPrivate")
    @ResponseBody
    public String queryRsaPrivate(String id){
        return merchantInfoManager.queryRsaPrivate(id);
    }
}
