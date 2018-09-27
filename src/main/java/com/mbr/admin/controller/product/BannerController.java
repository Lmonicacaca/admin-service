package com.mbr.admin.controller.product;

import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.app.Banner;
import com.mbr.admin.domain.app.Vo.BannerVo;
import com.mbr.admin.manager.app.BannerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "banner")
public class BannerController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${image_url}")
    private String image_url ;
    @Autowired
    private BannerManager bannerManager;


    //链接页面
    @RequestMapping(value = "initPage",method = RequestMethod.POST)
    public String initPage(){
        return "product/bannerList";
    }

    //查询所有的广告信息
    @RequestMapping(value="queryList",method = RequestMethod.POST)
    @ResponseBody
    public Object queryList(HttpServletRequest request, String url){
        Pageable page = new PageRequest(super.getPageNo(request)-1, super.getPageSize(request));
        Map<String, Object> map = bannerManager.queryAll(0, url, page);//查询所有状态码为0的信息
        PageResultDto result = result((List) map.get("list"), Long.valueOf(map.get("total").toString()));
        return result;
    }

    @RequestMapping(value="deleteBanner",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBanner(Long id){
        if(id==null){
            return failed("ID不能为空！");
        }
        bannerManager.deleteById(id);
        return success();
    }

    @RequestMapping(value="addOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Object addOrUpdate(BannerVo bannerVo, @RequestParam("file") MultipartFile[] multipartFile){
        System.out.println(bannerVo);
        try{
            if(multipartFile==null||multipartFile.length==0){
                if(bannerVo.getId()==null||bannerVo.getId()==0||bannerVo.getId().toString().equals("")){
                    return failed("图片不能为空！");
                }
                String result = bannerManager.addOrUpdate(bannerVo, null);
                if("success".equals(result)){
                    return success();
                }
            }
            String result = bannerManager.addOrUpdate(bannerVo, multipartFile[0]);
            if("success".equals(result)){
                return success();
            }

        }catch (MerchantException e){
            return failed(e.getMessage());
        }
        return success();
    }

    @RequestMapping(value="queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return bannerManager.queryChannel();
    }

       @RequestMapping(value="queryBannerType")
    @ResponseBody
    public Object queryBannerType(){

        return bannerManager.queryBannerType();
    }

    @RequestMapping(value="queryStatus")
    @ResponseBody
    public Object queryStatus(){

        return bannerManager.queryStatus();
    }

    @RequestMapping(value="queryById")
    @ResponseBody
    public Object queryById(Long id){
        if(id==null||id.toString().equals("")){
            return failed("id不能为空!");
        }
        Banner banner = bannerManager.queryById(id);
        if(banner!=null){
            return success(banner);
        }
        return failed("广告不存在！");
    }
}
