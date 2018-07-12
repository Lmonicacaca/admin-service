package com.mbr.admin.controller.merchant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.merchant.MerchantVsResource;
import com.mbr.admin.domain.merchant.Vo.MerchantVsResourceVo;
import com.mbr.admin.manager.merchant.MerchantVsResourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("merchantVsResource")
public class MerchantVsResourceController extends BaseController {

    @Autowired
    private MerchantVsResourceManager merchantVsResourceManager;

    @RequestMapping("initPage")
    public String initPage(){
        return "merchant/merchantVsResourceList";
    }
    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(String merchantIdSearch, HttpServletRequest request){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<MerchantVsResourceVo> list = merchantVsResourceManager.queryList(merchantIdSearch);
        PageResultDto result = new PageResultDto<MerchantVsResourceVo>(new PageInfo<MerchantVsResourceVo>(list));
        return result;
    }
    @RequestMapping("deleteMerchantVsResource")
    @ResponseBody
    public Object deleteMerchantVsResource(String id){
        if(id==null){
            return failed("ID不能为空");
        }
        int i = merchantVsResourceManager.deleteMerchantVsResource(id);
        if(i>0){
            return success();
        }

        return failed();
    }

    @RequestMapping("addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(MerchantVsResourceVo merchantVsResourceVo){
        System.out.println(merchantVsResourceVo);

        int i = merchantVsResourceManager.insertMerchantVsResource(merchantVsResourceVo);
        if(i>0&&i!=999){
            return success();
        }else if(i==999){
            return failed("添加的权限全部已存在");
        }
        else{
            return failed("添加权限失败");
        }

    }

    @RequestMapping("queryUrl")
    @ResponseBody
    public Object queryUrl(){
        return merchantVsResourceManager.queryAllUrl();
    }

    @RequestMapping("queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return merchantVsResourceManager.queryChannel();
    }

    @RequestMapping("queryMerchantId")
    @ResponseBody
    public Object queryMerchantId(){
        return merchantVsResourceManager.queryMerchantId();
    }
}
