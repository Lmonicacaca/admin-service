package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.Banner;
import com.mbr.admin.domain.app.Vo.BannerVo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BannerManager {

    public Map<String, Object> queryAll(int i, String url, Pageable page);

    void deleteById(Long id);

    List<Map<String,Object>> queryChannel();

    List<Map<String,Object>> queryBannerType();

    List<Map<String,Object>> queryStatus();

    String addOrUpdate(BannerVo bannerVo, MultipartFile multipartFile);
 }
