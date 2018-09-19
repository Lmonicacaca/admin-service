package com.mbr.admin.controller.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.app.AppUpdate;
import com.mbr.admin.domain.app.Vo.AppUpdateVo;
import com.mbr.admin.manager.app.AppUpdateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("appUpdate")
public class AppUpdateController extends BaseController {

    @Value("${image_url}")
    private String image_url;

    @Autowired
    private AppUpdateManager appUpdateManager;

    @RequestMapping("initPage")
    public String initPage(){
        return "product/appUpdateList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String versionSearch){
        Pageable page = new PageRequest(super.getPageNo(request)-1, super.getPageSize(request));
        Map<String, Object> map = appUpdateManager.queryList(versionSearch, image_url, page);
        PageResultDto result = result((List) map.get("list"), Long.valueOf(map.get("total").toString()));
        return result;
        
    }
    @RequestMapping("deleteAppUpdate")
    @ResponseBody
    public Object deleteAppUpdate(Long id){
        if(id==null){
            return failed("ID不能为空");
        }
        appUpdateManager.deleteAppUpdate(id);
        return success();
    }
    @RequestMapping("queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return appUpdateManager.queryChannel();
    }

    @RequestMapping("addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(AppUpdateVo appUpdateVo,HttpServletRequest request,@RequestParam("file")MultipartFile[] multipartFiles){
        System.out.println(appUpdateVo);
        if (appUpdateVo.getId()==null||appUpdateVo.getId().equals("")){
            if(multipartFiles==null||multipartFiles.length==0){
                return failed("请上传包和logo");
            }
            String url = multipartFiles[0].getOriginalFilename();
            String systemType = appUpdateVo.getAppUpdateType();
            if(!url.endsWith(".ipa")&&!url.endsWith(".apk")){
                return failed("上传包的格式有误，请重新上传！");
            }
            if(url.endsWith(".ipa")&&!systemType.equals("IOS")){
                return failed("上传的包与系统类型不符，请重新上传！");
            }
            if(url.endsWith(".apk")&&!systemType.equals("Android")){
                return failed("上传的包与系统类型不符，请重新上传！");
            }
            try {
                String result = appUpdateManager.addAppUpdate(appUpdateVo, multipartFiles);
                if(result.equals("success")){
                    return success();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                String result = appUpdateManager.addAppUpdate(appUpdateVo, null);
                if("success".equals(result)){
                    return success();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            return null;
    }

    @RequestMapping("queryById")
    @ResponseBody
    public Object queryById(Long id){
        if(id==null){
            return failed("ID不能为空");
        }
        AppUpdate appUpdate = appUpdateManager.queryById(id);
        return success(appUpdate);
    }
    @RequestMapping("queryType")
    @ResponseBody
    public Object queryType(){
        return appUpdateManager.queryType();
    }

    @RequestMapping("queryForce")
    @ResponseBody
    public Object queryForce(){
        return appUpdateManager.queryForce();
    }

    @RequestMapping("queryBuild")
    @ResponseBody
    public Object queryBuild(){
        return appUpdateManager.queryBuild();
    }

}
