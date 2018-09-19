package com.mbr.admin.common.storage;

import com.UpYun;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//@Component
public class Upyun extends CdnService {
    private static final Logger logger = LoggerFactory.getLogger(Upyun.class);

    // @Value("${upyun.cdn.endpoint}")
    // private String endpoint;
    private String savePath;
    private String cdnHost;

    private UpYun upYun;

    @Override
    public String getCdnHost() {
        return cdnHost;
    }

    public Upyun(UpYun client, String bucketName, String savePath, String cdnHost) {
        Assert.isTrue(client != null, "参数不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(bucketName), "参数不能为空");

        Assert.isTrue(StringUtils.isNotEmpty(cdnHost), "参数不能为空");
        if (StringUtils.isEmpty(savePath)) {
            this.savePath = "/";
        } else {
            this.savePath = savePath;
        }
        if (!this.savePath.endsWith("/")) {
            this.savePath = this.savePath + "/";
        }
        // this.bucketName = bucketName;
        this.upYun = client;
        this.cdnHost = cdnHost;
        logger.info("upyun inited");
    }

    public Optional<URL> getCdnUrl(final String saveKey) {
        return super.getCdnUrl(saveKey, cdnHost);
    }

    /**
     * 又拍云文件上传，需要验证
     *
     * @param saveKey
     * @param file
     * @param input
     * @param contentType
     * @return
     */
    public Optional<URL> upload(final String saveKey, final File file, final InputStream input, final String contentType) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("Content-Type", contentType);
            String path = savePath + saveKey;
            boolean result;
            if (file != null) {
                result = upYun.writeFile(path, file, true, params);
            } else {
                result = upYun.writeFile(path, input, true, params);
            }
            if (!result) {
                logger.warn("write 2 upyun exception result = {}", result);
                return Optional.empty();
            }
            // TODO 文件上传需要验证
            return this.getCdnUrl(path, cdnHost);
        } catch (Exception e) {
            logger.warn("upyun put [{}] fail! Cause : {}", saveKey, e.getMessage());
            return Optional.empty();
        }
    }

    public synchronized final boolean delete(String path) {
        try {
            if (StringUtils.isEmpty(path)) {
                throw new RuntimeException("路径为空或文件不存在");
            }
            if (!path.startsWith("/")) {
                path = '/' + path;
            }
            return upYun.deleteFile(path);
        } catch (Exception e) {
            logger.warn("upyun delete [{}] fail! Cause : {}", path, e.getMessage());
            return false;
        }
    }

}
