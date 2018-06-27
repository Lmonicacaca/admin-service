package com.mbr.admin.controller.merchant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.merchant.Vo.MerchantHelpVo;
import com.mbr.admin.manager.merchant.MerchantHelpManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("merchantHelp")
public class MerchantHelpController extends BaseController {

    @Autowired
    private MerchantHelpManager merchantHelpManager;

    @RequestMapping("initPage")
    public String initPage(){
        return "merchant/merchantHelpList";
    }
    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(String titleSearch, HttpServletRequest request){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<MerchantHelpVo> coinTmpList = merchantHelpManager.queryList(titleSearch);
        PageResultDto result = new PageResultDto<MerchantHelpVo>(new PageInfo<MerchantHelpVo>(coinTmpList));
        return result;
    }
    @RequestMapping("queryContent")
    @ResponseBody
    public String queryContent(Integer id){

        return merchantHelpManager.queryContent(id);
    }

    @RequestMapping("deleteMerchantHelp")
    @ResponseBody
    public Object deleteMerchantHelp(Integer id){
        if(id==null){
            return failed("ID不能为空");
        }
        int i = merchantHelpManager.deleteMerchantHelp(id);
        if(i>0){
            return success();
        }
        return failed("删除失败");
    }

}
