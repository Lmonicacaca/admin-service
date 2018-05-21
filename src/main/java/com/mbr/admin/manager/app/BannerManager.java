package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.Banner;

import java.util.List;
import java.util.Map;

public interface BannerManager {
        public List<Banner> queryAll(int i,String url);
        public void deleteBanner(Long id);
        public Banner queryById(Long id);
        public int countAll();
        public Banner saveOrUpdate(Banner banner);
}
