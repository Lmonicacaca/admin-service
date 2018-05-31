package com.mbr.admin.controller.merchant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.TimestampPkGenerator;
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
    public Object queryList(HttpServletRequest request,String merchantidSearch,String channelSearch){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<Map<String, Object>> withDrawList = withDrawManager.queryList(merchantidSearch, channelSearch);
        PageResultDto result = new PageResultDto<Map<String, Object>>(new PageInfo<Map<String, Object>>(withDrawList));
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
        Map<String, Object> map = withDrawManager.selectById(id);
        return success(map);
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
    @RequestMapping("addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(WithDraw withDraw){
        if(withDraw.getId()!=null){
            withDraw.setUpdateTime(new Date());
            int i = withDrawManager.updateById(withDraw);
            if(i>0){
                return success();
            }else{
                return failed();
            }
        }
        else{
            Long id = new TimestampPkGenerator().next(getClass());
            withDraw.setId(id);
            int i = withDrawManager.saveWithDraw(withDraw);
            if (i > 0) {
                return success();
            }else{
                return failed();
            }
        }
    }

}
