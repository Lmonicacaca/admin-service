package com.mbr.admin.common.storage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

/**
 * 描述:本地存储
 *
 * @outhor liuji
 * @create 2018-06-25 14:51
 */
public class LocalStorage extends CdnService {
    private static final Logger logger = LoggerFactory.getLogger(LocalStorage.class);
    // 文件保存根目录
    private String saveRoot;
    /**
     * 文件对外域名
     */
    private String cdnHost;

    @Override
    public String getCdnHost() {
        return cdnHost;
    }

    public LocalStorage(String saveRoot, String cdnHost) {
        Assert.isTrue(StringUtils.isNotEmpty(saveRoot), "参数不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(cdnHost), "参数不能为空");
        this.saveRoot = saveRoot;
        this.cdnHost = cdnHost;
        logger.info("local storage inited");
    }

    @Override
    public Optional<URL> getCdnUrl(String saveKey) {
        return super.getCdnUrl(saveKey, cdnHost);
    }

    /**
     * 上传
     *
     * @param saveKey
     * @param file
     * @param input
     * @param contentType
     * @return
     */
    @Override
    public Optional<URL> upload(String saveKey, File file, InputStream input, String contentType) {
        try {

            File dis = new File(saveRoot, saveKey);
            // 创建父级目录
            if (!dis.getParentFile().exists()) {
                dis.getParentFile().mkdirs();
            }
            if (file != null) {
                FileUtils.copyFile(file, dis);
            } else {
                FileUtils.copyInputStreamToFile(input, dis);
            }
            return this.getCdnUrl(saveKey);
        } catch (Exception e) {
            logger.warn("local put [{}] fail! Cause : {}", saveKey, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * 删除
     *
     * @param path
     * @return
     */
    @Override
    public boolean delete(String path) {
        File dis = new File(saveRoot, path);
        if (dis.exists()) {
            dis.delete();
        }
        return true;
    }
}
