package com.mbr.admin.controller.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.utils.FileUpload;
import com.mbr.admin.common.utils.TimestampPkGenerator;
import com.mbr.admin.domain.app.Banner;
import com.mbr.admin.manager.app.BannerManager;
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
import java.util.*;

@Controller
@RequestMapping(value = "banner")
public class BannerController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${image_url}")
    private String image_url ;
    @Autowired
    private BannerManager bannerManager;

    @Autowired
    private FileUpload fileUpload;

    //链接页面
    @RequestMapping(value = "initPage",method = RequestMethod.POST)
    public String initPage(){
        return "product/bannerList";
    }

    //查询所有的广告信息
    @RequestMapping(value="queryList",method = RequestMethod.POST)
    @ResponseBody
    public Object queryList(HttpServletRequest request, String url){
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<Banner> bannerList = bannerManager.queryAll(0,url);//查询所有状态码为0的信息
        PageResultDto result = new PageResultDto<Banner>(new PageInfo<Banner>(bannerList));
        return result;
    }


    //根据id查询对应的banner
    @RequestMapping(value="queryById",method = RequestMethod.POST)
    @ResponseBody
    public Object queryById(Long id){
        if (id == null) {
            return super.failed("广告ID 为空！");
        }else{
            Banner banner = bannerManager.queryById(id);
            if(banner !=null){
                return success(banner);
            }else{
                return failed("无相关记录");
            }
        }

    }
    //删除广告信息
    @RequestMapping(value = "deleteBanner",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBanner(Long id){
        if(id == null){
            return super.failed("广告ID为空！");
        }
        bannerManager.deleteBanner(id);
        return success();
    }

    //查询广告的类型
    @RequestMapping(value = "queryBannerType",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> queryBannerType(){

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        List<String> listText = new ArrayList<>();
        listText.add("余额");
        listText.add("商家");
        listText.add("支付");
        for(int i=1;i<=3;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",i);
            map.put("text",listText.get(i-1));
            list.add(map);
        }
        return list;
    }

    //保存或更新banner
    @RequestMapping(value = "addOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Object addOrUpdate(Banner banner,HttpServletRequest request){
        System.out.println(banner);
        Long id = banner.getId();
        if(id == null){
            id=new TimestampPkGenerator().next(getClass());
            banner.setId(id);
        }
        int count = bannerManager.countAll();
        int orderBy = banner.getOrderBy();
        if(orderBy == 0){
            orderBy = count%3==0?1:count%3;
        }
        Map<String, MultipartFile> mapFiles = fileUpload.getFile(request);
        if(mapFiles!=null){
            String json = fileUpload.httpClientUploadFile(mapFiles);
            Map map = JSONObject.toJavaObject(JSON.parseObject(json), Map.class);
            if((Integer)map.get("code")==200){
                Map<String,String> mapRequest = (Map<String,String>)map.get("data");
                for (Map.Entry<String,String> entry:mapRequest.entrySet()) {
                    banner.setImage(entry.getValue());

                }
            }
        }
        else {
            banner.setImage(banner.getImage());
        }

        banner.setCreateTime(new Date());
        banner.setOrderBy(orderBy);
        System.out.println(banner);
        Banner saveOrUpdate = bannerManager.saveOrUpdate(banner);

        if(saveOrUpdate != null){
            return success();
        }
        return failed("添加广告失败");
    }

    //返回图片的url
    @RequestMapping(value = "queryUrl",method = RequestMethod.GET)
    @ResponseBody
    public String queryUrl(){
        return image_url;
    }

}
