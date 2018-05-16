package com.mbr.admin.controller.product;


import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.domain.vo.PageResult;
import com.mbr.admin.common.dto.BaseResult;
import com.mbr.admin.feign.ProductFeign;
import com.mbr.admin.feign.dto.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "product")
public class ProductController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductFeign productFeign;
    @Value("${image_url}")
    private String image_url;

    @RequestMapping(value = "initPage",method = RequestMethod.POST)
    public String initPage(){
        return "product/list";
    }
    @RequestMapping(value = "queryList", method = RequestMethod.POST)
    @ResponseBody
    public Object queryList(HttpServletRequest request,  String coinNameBut) {
        int pageNo = getPageNo(request)-1;
        BaseResult<PageResult<Product>> coinList = productFeign.queryByAdminForPage(coinNameBut, pageNo, getPageSize(request));
        if (coinList.getCode().equals("200")) {
            PageResult<Product> data = coinList.getData();
            return result(data.getList(),Long.valueOf(data.getTotal()));
        }else{
            return result(null,0L);
        }
    }


    @RequestMapping(value = "queryById", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Product> queryById(HttpServletRequest request, Long id) {
        BaseResult<Product> baseResult = new BaseResult<>();
        BaseResult<Product> productBaseResult = productFeign.queryById(id);
        if (productBaseResult.getData()!=null){
            Product p = productBaseResult.getData();
            p.setCoinAvatarUrl(image_url+p.getCoinAvatarUrl());
            baseResult.setData(p);
            baseResult.setCode("200");
        }else{
            baseResult.setCode(productBaseResult.getCode());
            baseResult.setMessage(productBaseResult.getMessage());
        }

        return baseResult;
    }

}
