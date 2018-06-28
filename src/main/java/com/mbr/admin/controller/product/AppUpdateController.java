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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<AppUpdate> list = appUpdateManager.queryList(versionSearch,image_url);
        PageResultDto result = new PageResultDto<AppUpdate>(new PageInfo<AppUpdate>(list));
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
        if(appUpdateVo.getAppUpdateType().equals("Android")){
            if(multipartFiles.length>0){
                if(!multipartFiles[0].getOriginalFilename().endsWith(".apk")){
                    return failed("安装包格式有误！");
                }
            }

        }
        if(appUpdateVo.getAppUpdateType().equals("IOS")){
            if(multipartFiles.length>0){
                if(!multipartFiles[0].getOriginalFilename().endsWith(".ipa")&&!multipartFiles[0].getOriginalFilename().endsWith(".plist")){
                    return failed("安装包格式有误！");
                }
                if(!multipartFiles[1].getOriginalFilename().endsWith(".ipa")&&!multipartFiles[1].getOriginalFilename().endsWith(".plist")){
                    return failed("安装包格式有误！");
                }
            }

        }
        int i = appUpdateManager.addOrUpdate(appUpdateVo, request, multipartFiles);
        if(i>0){
            return success();
        }

        return failed("添加或更新失败");
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
}
