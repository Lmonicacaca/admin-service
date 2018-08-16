package com.mbr.admin.controller.product;

import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.app.ProductVsChannel;
import com.mbr.admin.domain.app.Vo.ProductVSChannelVo;
import com.mbr.admin.manager.app.ProductVSChannelManager;
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
@RequestMapping("productVsChannel")
public class ProductVSChannelController extends BaseController {

    @Autowired
    private ProductVSChannelManager productVSChannelManager;

    @RequestMapping("/initPage")
    public String initPage(){
        return "product/channelVSproductList";
    }

    @RequestMapping("/queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return productVSChannelManager.queryChannel();
    }

    @RequestMapping("/queryProductId")
    @ResponseBody
    public Object queryProductId(){
        List<Map<String, Object>> list = productVSChannelManager.queryProductId();
        return productVSChannelManager.queryProductId();
    }
    @RequestMapping("/queryOnlineStatus")
    @ResponseBody
    public Object queryOnlineStatus(){
        List<Map<String, Object>> list = productVSChannelManager.queryProductId();
        return productVSChannelManager.queryOnlineStatus();
    }
    @RequestMapping("/queryIsForceShow")
    @ResponseBody
    public Object queryIsForceShow(){
        List<Map<String, Object>> list = productVSChannelManager.queryProductId();
        return productVSChannelManager.queryIsForceShow();
    }
    @RequestMapping("/queryMerchantShow")
    @ResponseBody
    public Object queryMerchantShow(){
        List<Map<String, Object>> list = productVSChannelManager.queryProductId();
        return productVSChannelManager.queryMerchantShow();
    }
    @RequestMapping("/queryList")
    @ResponseBody
    public Object queryList(Long channelQuery, Long productQuery, HttpServletRequest request){
        System.out.println(channelQuery+" "+productQuery);
        Pageable page = new PageRequest(super.getPageNo(request)-1, super.getPageSize(request));
        Map<String, Object> map = productVSChannelManager.queryList(channelQuery,productQuery, page);
        PageResultDto result = result((List) map.get("list"), Long.valueOf(map.get("total").toString()));
        return result;
    }

    @RequestMapping("/addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(ProductVSChannelVo productVSChannelVo){
        System.out.println(productVSChannelVo);
        try{
            String result = productVSChannelManager.addOrUpdate(productVSChannelVo);
            if(result.equals("success")){
                return success();
            }
        }catch (MerchantException e){
            return failed(e.getMessage());
        }
        return failed();
    }

    @RequestMapping("/deleteProductVsChannel")
    @ResponseBody
    public Object deleteProductVsChannel(Long id){
        productVSChannelManager.deleteProductVSChannel(id);
        return success();
    }

    @RequestMapping("/queryById")
    @ResponseBody
    public Object queryById(Long id){
        ProductVsChannel productVsChannel = productVSChannelManager.queryById(id);
        return success(productVsChannel);
    }
}
