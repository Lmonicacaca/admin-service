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
import com.mbr.admin.feign.dto.product.ProductVo;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "product")
public class ProductController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductFeign productFeign;
    @Value("${image_url}")
    private String image_url;
    @Autowired
    private FileUpload fileUpload;

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
            List<Product> list = data.getList();
            for(int i =0;i<list.size();i++){
                if(list.get(i).getCoinAvatarUrl()!=null&&list.get(i).getCoinAvatarUrl()!=""){
                    list.get(i).setCoinAvatarUrl(image_url+list.get(i).getCoinAvatarUrl());
                }
            }

            return result(list,Long.valueOf(data.getTotal()));
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
    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteById(Long id){
        BaseResult baseResult = productFeign.deleteById(id);
        if(baseResult!=null){
            return success();
        }
        return failed();
    }
    @RequestMapping(value = "addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Object addOrUpdate(ProductVo productVo,HttpServletRequest request){
        System.out.println(productVo);

        if(productVo.getId()==null){
            Long id = new TimestampPkGenerator().next(getClass());
            String logoUrl = "";
            logoUrl = getLogoUrl(request);
            if(logoUrl==""){
                return failed("图片上传失败");
            }if(logoUrl.equals("none")){
                logoUrl="";
            }
            Product product = createProduct(productVo, id, logoUrl);
            BaseResult save = productFeign.save(product);
            if(save!=null){
                return success();
            }else{
                return failed("添加数据失败");
            }

        }else{
            String logoUrl = "";
            logoUrl = getLogoUrl(request);
            if(logoUrl==""){
                return failed("图片上传失败");
            }if(logoUrl.equals("none")){
                logoUrl="";
            }
            Product product = createProduct(productVo, null, logoUrl);
            BaseResult save = productFeign.save(product);
            if(save!=null){
                return success();
            }else{
                return failed("添加数据失败");
            }
        }
    }

    public Product createProduct(ProductVo productVo, Long id, String logoUrl){
        Product product = new Product();
        if(id!=null){
            product.setId(id);
        }else{
            product.setId(productVo.getId());
        }
        if(logoUrl!=null&&logoUrl!=""){
            product.setCoinAvatarUrl(logoUrl);
        }else{
            String url = "/"+productVo.getOldLogo().substring(productVo.getOldLogo().indexOf("d"));
            product.setCoinAvatarUrl(url);
        }
        product.setCreateTime(new Date());
        product.setCoinName(productVo.getCoinName());
        product.setCoinType(productVo.getCoinType());
        product.setCoinDescription(productVo.getCoinDescription());
        product.setOnlineStatus(0);
        product.setChainType(productVo.getChainType());
        product.setTokenAddress(productVo.getTokenAddress());
        product.setCoinDecimals(productVo.getCoinDecimals());
        product.setIsForceShow(0);
        product.setCoinErc20(productVo.getCoinErc20());
        product.setMerchantShow(false);
        product.setGasLimit(productVo.getGasLimit());
        product.setDef(productVo.getDef());

        return product;
    }

    public String getLogoUrl(HttpServletRequest request){
        Map<String, MultipartFile> fileMap = fileUpload.getFile(request);
        String logoUrl = "";
        if(fileMap.size()!=0){
            String json = fileUpload.httpClientUploadFile(fileMap);
            Map map = JSONObject.toJavaObject(JSON.parseObject(json), Map.class);
            if((Integer)map.get("code")==200){
                Map<String,String> mapRequest = (Map<String,String>)map.get("data");
                for (Map.Entry<String,String> entry:mapRequest.entrySet()) {
                    logoUrl = entry.getValue();
                }
            }

        }else{
            return "none";
        }
        return logoUrl;
    }

}
