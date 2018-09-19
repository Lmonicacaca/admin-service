package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.AppUpdate;
import com.mbr.admin.domain.app.Vo.AppUpdateVo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface AppUpdateManager {
    public Map<String,Object> queryList(String version, String image_url, Pageable page);

    public void deleteAppUpdate(Long id);

    public List<Map<String,Object>> queryChannel();

    public AppUpdate queryById(Long id);

    public List<Map<String,Object>> queryType();

    public List<Map<String,Object>> queryForce();

    public List<Map<String,Object>> queryBuild();

    public String addAppUpdate(AppUpdateVo appUpdateVo,MultipartFile[] files);
}
