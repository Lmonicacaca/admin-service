package com.mbr.admin.controller.merchant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.merchant.MerchantCoin;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.manager.merchant.MerchantCoinManager;
import com.mbr.admin.manager.security.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public Object queryList(HttpServletRequest request,String merchant_Id,String channelSearch){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<MerchantCoin> merchantCoins = merchantCoinManager.queryList(merchant_Id,channelSearch);
        PageResultDto result = new PageResultDto<MerchantCoin>(new PageInfo<MerchantCoin>(merchantCoins));
        return result;
    }

    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(Long id){
        if (id == null) {
            return super.failed("ID 为空！");
        }else{
            MerchantCoin merchantCoin = merchantCoinManager.selectById(id);
            if(merchantCoin !=null){
                return success(merchantCoin);
            }else{
                return failed("无相关记录");
            }
        }
    }
    @RequestMapping("deleteMerchantCoin")
    @ResponseBody
    public Object deleteMerchantCoin(Long id){
        MerchantCoin merchantCoin = merchantCoinManager.selectById(id);
        merchantCoin.setStatus(1);
        merchantCoinManager.updateById(merchantCoin);

        return success();
    }

    @RequestMapping("queryCoin")
    @ResponseBody
    public Object queryCoin(){
        List<Map<String,String>> allProduct = merchantCoinManager.findAllProduct();
        return allProduct;
    }

    @RequestMapping("queryChannel")
    @ResponseBody
    public Object queryChannel(){
        List<Map<String,String>> allChannel = merchantCoinManager.findAllChannel();
        return allChannel;
    }
    @RequestMapping(value = "addOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Object addOrUpdate(MerchantCoin merchantCoin){
        if(merchantCoin.getId()!=null){
            System.out.println(merchantCoin);
            long coinId = merchantCoin.getCoinId();
            Product coin = merchantCoinManager.findCoinById(coinId);
            merchantCoin.setTokenAddress(coin.getTokenAddress());
            merchantCoin.setCoinName(coin.getCoinName());
            merchantCoin.setUpdateTime(new Date());
            SecurityUserDetails securityUserDetails =(SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            merchantCoin.setUpdateUserName(securityUserDetails.getUsername());
            int i = merchantCoinManager.updataMerchantCoin(merchantCoin);

            if(i>0){
                return success();
            }else{
                return failed("更新失败");
            }

        }else{

            merchantCoin.setStatus(0);
            long coinId = merchantCoin.getCoinId();
            Product coin = merchantCoinManager.findCoinById(coinId);
            merchantCoin.setCoinName(coin.getCoinName());
            merchantCoin.setTokenAddress(coin.getTokenAddress());
            long id = merchantCoinManager.getMerchantCoinCount()+1;
            merchantCoin.setId(id);
            System.out.println(merchantCoin);
            int i = merchantCoinManager.saveMerchantCoin(merchantCoin);
            if(i>0){
                return success();
            }else{
                return failed("添加失败");
            }
        }

    }

    @RequestMapping("queryUser")
    @ResponseBody
    public List<Map<String,Object>> queryUser(){
        return merchantCoinManager.queryUser();
    }
}
