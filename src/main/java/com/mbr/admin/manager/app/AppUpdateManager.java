package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.AppUpdate;
import com.mbr.admin.domain.app.Vo.AppUpdateVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface AppUpdateManager {
    public List<AppUpdate> queryList(String version,String image_url);

    public void deleteAppUpdate(Long id);

    public List<Map<String,Object>> queryChannel();

    public int addOrUpdate(AppUpdateVo appUpdateVo, HttpServletRequest request,MultipartFile[] multipartFiles);

    public AppUpdate queryById(Long id);

    public List<Map<String,Object>> queryType();

    public List<Map<String,Object>> queryForce();
}
