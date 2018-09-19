package com.mbr.admin.common.storage;

import com.mbr.admin.common.utils.MimeUtils;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * 描述:${DESCRIPTION}
 *
 * @outhor liuji
 * @create 2018-06-25 14:20
 */
public abstract class CdnService {

    private static final Logger logger = LoggerFactory.getLogger(CdnService.class);

    public abstract Optional<URL> getCdnUrl(final String saveKey);


    public abstract String getCdnHost();

    /**
     * 生成文件保存路径
     *
     * @param fileName
     * @return
     */
    public static String genSaveKey(final String mkdirs, final String fileName) {
       return mkdirs+fileName;
    }

    /**
     * 生成随机文件名
     *
     * @param type        大分类，自定义
     * @param contentType 常用文件的mime类型，参考MimeUtils类
     * @return
     */
    public static String genRandamKey(final String type, final String contentType) {
        String today = DateFormatUtils.format(new Date(), "yyyyMMdd");
        return new StringBuilder().append(type).append("/").append(today).append("/").append(UUID.randomUUID()
                .toString()).append('.').append(MimeUtils.getFileExtension(contentType)).toString();
    }

    /**
     * 获取生成的URL地址
     *
     * @param saveKey
     * @param cdnHost
     * @return
     */
    public Optional<URL> getCdnUrl(final String saveKey, final String cdnHost) {
        try {
            HttpUrl.Builder build = new HttpUrl.Builder().host(cdnHost).scheme("http").addPathSegments(saveKey);
            return Optional.of(build.build().url());
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    /**
     * 文件上传
     *
     * @param saveKey
     * @param multipartFile
     * @param contentType
     * @return
     */
    public Optional<URL> put(final String saveKey, final MultipartFile multipartFile, final String contentType) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                InputStream inp = new BufferedInputStream(new ByteArrayInputStream(multipartFile.getBytes()));
                return this.put(saveKey, inp, contentType);
            } catch (IOException e) {
                logger.error("Upload file fail!", e);
            } finally {
            }
        }
        return Optional.empty();
    }

    /**
     * 上传
     *
     * @param saveKey
     * @param input
     * @param contentType
     * @return
     */
    public Optional<URL> put(final String saveKey, final InputStream input, final String contentType) {
        logger.info("upload path = {}", saveKey);
        if (org.apache.commons.lang3.StringUtils.isEmpty(saveKey)) {
            logger.warn("Empty path");
            return null;
        }
        return this.upload(saveKey, null, input, contentType);
    }

    /**
     * 上传
     *
     * @param saveKey
     * @param file
     * @param contentType
     * @return
     */
    public Optional<URL> put(final String saveKey, final File file, final String contentType) {
        logger.info("upload path = {}", saveKey);
        if (org.apache.commons.lang3.StringUtils.isEmpty(saveKey)) {
            logger.warn("Empty path");
            return null;
        }

        if (file == null || !file.exists()) {
            logger.warn("File not exists");
            return null;
        }

        return this.upload(saveKey, file, null, contentType);
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
    public abstract Optional<URL> upload(final String saveKey, final File file, final InputStream input, final String
            contentType);

    /**
     * 删除
     *
     * @param path
     * @return
     */
    public abstract boolean delete(String path);

}
