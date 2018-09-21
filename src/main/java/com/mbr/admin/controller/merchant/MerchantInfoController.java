package com.mbr.admin.controller.merchant;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.merchant.MerchantInfo;
import com.mbr.admin.domain.merchant.MerchantVsResource;
import com.mbr.admin.domain.merchant.Vo.MerchantInfoVo;
import com.mbr.admin.manager.merchant.MerchantInfoManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public Object queryList(HttpServletRequest request,String nameSearch,String idSearch){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<MerchantInfoVo> payBillList = merchantInfoManager.queryList(nameSearch,idSearch);
        PageResultDto result = new PageResultDto<MerchantInfoVo>(new PageInfo<MerchantInfoVo>(payBillList));
        return result;
    }

    @RequestMapping("deleteMerchant")
    @ResponseBody
    public Object deleteMerchant(String id){

        if(id==null){
            return failed("ID不能为空");
        }
        try {
            boolean result = merchantInfoManager.deleteMerchantInfo(id);
            if(result==true){
                return success();
            }
            return failed();
        } catch (MerchantException e) {
            return failed(e.getMessage());
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

    @RequestMapping("queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return merchantInfoManager.queryChannel();
    }
    @RequestMapping("queryIsShow")
    @ResponseBody
    public Object queryIsShow(){
        return merchantInfoManager.queryIsShow();
    }



    @RequestMapping("addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(MerchantInfoVo merchantInfoVo, HttpServletRequest request){
        System.out.println(merchantInfoVo);
        if(merchantInfoVo.getIsShow()==null){
            merchantInfoVo.setIsShow(0);
        }
        try{
            String result = merchantInfoManager.addOrUpdate(merchantInfoVo, request);
            if(result.equals("imgFailed")){
                return failed("图片上传失败");
            }else{
                return success();
            }

        }catch(MerchantException e){
            return failed(e.getMessage());
        }

    }

    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(String id){
        if(id==null||id.equals("")){
            return failed("ID不能为空");
        }
        MerchantInfo merchantInfo = merchantInfoManager.queryById(id);
        if(merchantInfo!=null){
            return success(merchantInfo);
        }
        return failed("查询失败");
    }


    /**
     * 审核商户，为商户分配渠道号
     * @param merchantInfo
     * @return
     */
    @RequestMapping("auditMerchant")
    @ResponseBody
    public Object auditMerchant(MerchantInfo merchantInfo){
        System.out.println(merchantInfo);
        String result = merchantInfoManager.auditMerchant(merchantInfo);
        if("success".equals(result)){
            return success();
        }
        return failed("审核失败");
    }
}
