package com.mbr.admin.controller.product;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.domain.vo.PageResult;
import com.mbr.admin.common.dto.BaseResult;
import com.mbr.admin.common.utils.FileUpload;
import com.mbr.admin.common.utils.TimestampPkGenerator;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(value = "product")
public class ProductController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private FileUpload fileUpload;
    @Value("${server_url}")
    private String server_url;

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
            p.setCoinAvatarUrl(server_url+p.getCoinAvatarUrl());
            baseResult.setData(p);
            baseResult.setCode("200");
        }else{
            baseResult.setCode(productBaseResult.getCode());
            baseResult.setMessage(productBaseResult.getMessage());
        }

        return baseResult;
    }

    @RequestMapping(value = "addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Object addOrUpdate(Product product,HttpServletRequest request){
        Map<String, MultipartFile> mapFiles = fileUpload.getFile(request);
        if (mapFiles!=null&&mapFiles.size()>0) {
            String json = fileUpload.httpClientUploadFile(mapFiles);
            Map map = JSONObject.toJavaObject(JSON.parseObject(json), Map.class);
            if ((Integer) map.get("code") == 200) {
                Map<String, String> mapReQUEST = (Map<String, String>) map.get("data");
                for (Map.Entry<String, String> entry : mapReQUEST.entrySet()) {
                    product.setCoinAvatarUrl(entry.getValue());
                }
            }
        }else{
            if (product.getId()!=null){
                BaseResult<Product>  p = this.productFeign.queryById(product.getId());
                if (p.getData()!=null){
                    product.setCoinAvatarUrl(p.getData().getCoinAvatarUrl());
                }
            }
        }

        try {
            BaseResult baseResult = productFeign.save(product);
            if (baseResult.getCode().equals("200")) {
                return success();
            }else{
                return failed();
            }
        }catch (Exception e){
            e.printStackTrace();
            return failed(e.getMessage());
        }

    }

    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Product> deleteById(HttpServletRequest request, Long id){

        BaseResult productBaseResult = productFeign.deleteById(id);


        return productBaseResult;
    }
}
