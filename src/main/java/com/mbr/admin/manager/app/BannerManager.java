package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.Banner;
import com.mbr.admin.domain.app.Vo.BannerVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BannerManager {
        public Map<String,Object> queryAll(int i, String url, Pageable page);
        public void deleteBanner(Long id);
        public Banner queryById(Long id);
        public int countAll();
        public Banner saveOrUpdate(HttpServletRequest request, BannerVo bannerVo);

        public List<Map<String,Object>> queryChannel();

        public List<Map<String,Object>> queryBannerType();
}
