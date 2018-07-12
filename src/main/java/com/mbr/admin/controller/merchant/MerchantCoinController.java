package com.mbr.admin.controller.merchant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.merchant.MerchantCoin;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.domain.merchant.Vo.MerchantCoinVo;
import com.mbr.admin.manager.merchant.MerchantCoinManager;
import com.mbr.admin.manager.security.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("merchantCoin")
public class MerchantCoinController extends BaseController {

    @Value("${channel}")
    private Long channel;

    @Autowired
    private MerchantCoinManager merchantCoinManager;

    @RequestMapping("initPage")
    public String initPage(){
        return "merchant/merchantCoinList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String merchant_Id,String nameSearch){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<MerchantCoinVo> merchantCoins = merchantCoinManager.queryList(merchant_Id,nameSearch);
        PageResultDto result = new PageResultDto<MerchantCoinVo>(new PageInfo<MerchantCoinVo>(merchantCoins));
        return result;
    }

    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(String id){
        if (id == null) {
            return super.failed("ID 为空！");
        }else{
            MerchantCoinVo merchantCoinVo = merchantCoinManager.selectById(id);
            if(merchantCoinVo !=null){
                return success(merchantCoinVo);
            }else{
                return failed("无相关记录");
            }
        }
    }
    @RequestMapping("deleteMerchantCoin")
    @ResponseBody
    public Object deleteMerchantCoin(Long id){
        if(id == null){
            return failed("ID为空");
        }
        int i = merchantCoinManager.deleteById(id);
        if(i>0){
            return success();
        }else{
            return failed();
        }

    }

    @RequestMapping("queryCoin")
    @ResponseBody
    public Object queryCoin(){
        List<Map<String,String>> allProduct = merchantCoinManager.findAllProduct();
        return allProduct;
    }


    @RequestMapping("queryMerchantId")
    @ResponseBody
    public Object queryMerchantId(){
        System.out.println(channel);
        List<Map<String,Object>> allMerchantId= merchantCoinManager.queryMerchantId();
        return allMerchantId;
    }

    @RequestMapping(value = "addOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Object addOrUpdate(MerchantCoinVo merchantCoinVo){
        System.out.println(merchantCoinVo);
        if(merchantCoinVo.getStatus()==null){
            merchantCoinVo.setStatus(0);
        }
        String result = merchantCoinManager.addOrUpdate(merchantCoinVo);
        if(result.equals("coinNotExist")){
            return failed("币种不存在");
        }else if(result.equals("insertFailed")){
            return failed("插入数据失败");
        }else if(result.equals("merchantCoinExist")){
            return failed("已存在相同数据");
        } else if(result.equals("updateFailed")){
            return  failed("更新数据失败");
        }else{
            return success();
        }

    }

}
