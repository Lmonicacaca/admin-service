package com.mbr.admin.controller.merchant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.merchant.Vo.WithDrawVo;
import com.mbr.admin.domain.merchant.WithDraw;
import com.mbr.admin.manager.merchant.WithDrawManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("withDraw")
public class WithDrawController extends BaseController {

    @Autowired
    private WithDrawManager withDrawManager;
    @RequestMapping("initPage")
    public String initPage(){
        return "merchant/withDrawList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String merchantidSearch,String merchantnameSearch){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<WithDrawVo> withDrawVoList = withDrawManager.queryList(merchantidSearch, merchantnameSearch);
        PageResultDto result = new PageResultDto<WithDrawVo>(new PageInfo<WithDrawVo>(withDrawVoList));
        return result;
    }

    @RequestMapping("deleteWithDraw")
    @ResponseBody
    public Object deleteWithDraw(Long id){
        if(id==null){
            return failed("ID为空");
        }
        withDrawManager.deleteById(id);
       return success();


    }

    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(Long id){
        if(id==null){
            return failed("ID不能为空");
        }
        Object withdraw = withDrawManager.selectById(id);
        return success(withdraw);
    }

    @RequestMapping("queryChannel")
    @ResponseBody
    public List<Map<String,Object>> queryChannel(){
        return withDrawManager.queryChannel();
    }

    @RequestMapping("queryCoin")
    @ResponseBody
    public List<Map<String,Object>> queryCoin(){
        return withDrawManager.queryCoin();
    }

    @RequestMapping("queryMerchant")
    @ResponseBody
    public List<Map<String,Object>> queryMerchant(){
        return withDrawManager.queryMerchant();
    }



    @RequestMapping("addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(WithDraw withDraw){
        System.out.println(withDraw);
        String result = withDrawManager.addOrUpdate(withDraw);
        if("withdrawExists".equals(result)){
            return failed("已存在相同数据");
        }else if("insertFailed".equals(result)){
            return failed("添加数据失败");
        }else if("updateFailed".equals(result)){
            return failed("数据更新失败");
        }else{
            return success();
        }
    }

}
