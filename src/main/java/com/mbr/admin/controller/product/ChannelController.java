package com.mbr.admin.controller.product;

import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.merchant.Vo.ChannelVo;
import com.mbr.admin.manager.merchant.ChannelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("channel")
public class ChannelController extends BaseController {

    @Autowired
    private ChannelManager channelManager;

    @RequestMapping("/initPage")
    public String initPage(){
        return "product/channelList";
    }

    @RequestMapping("/queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String merchantNameSearch,String appName){
        Pageable page = new PageRequest(super.getPageNo(request)-1, super.getPageSize(request));
        Map<String, Object> map = channelManager.queryAllChannel(merchantNameSearch, appName, page);
        PageResultDto result = result((List) map.get("list"), Long.valueOf(map.get("total").toString()));
        return result;
    }

    @RequestMapping("/addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(ChannelVo channelVo){
        System.out.println(channelVo);
        try{
            String result = channelManager.addOrUpdate(channelVo);
            if("success".equals(result)){
                return success();
            }
        }catch(MerchantException e){
            return failed(e.getMessage());
        }

        return null;
    }

    @RequestMapping("/queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return channelManager.findAllChannel();
    }

    @RequestMapping("/queryMerchantId")
    @ResponseBody
    public Object queryMerchantId(){
        return channelManager.queryMerchantId();
    }

    @RequestMapping("/deleteChannel")
    @ResponseBody
    public Object deleteChannel(Long id){
        channelManager.deleteChannel(id);
        return success();
    }
    @RequestMapping("/queryById")
    @ResponseBody
    public Object queryById(Long id){
        return success(channelManager.queryById(id));
    }

}
