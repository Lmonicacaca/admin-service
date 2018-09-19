package com.mbr.admin.common.storage;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public class Aliyun extends CdnService {
    private static final Logger logger = LoggerFactory.getLogger(Aliyun.class);


    private String bucketName;

    private String cdnHost;

    @Autowired
    private OSSClient ossClient;

    public Aliyun(OSSClient client, String bucketName, String cdnHost) {
        Assert.isTrue(client != null, "参数不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(bucketName), "参数不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(cdnHost), "参数不能为空");
        this.bucketName = bucketName;
        this.ossClient = client;
        this.cdnHost = cdnHost;
        logger.info("aliyun oss inited");
    }

    public Optional<URL> getCdnUrl(final String saveKey) {
        return super.getCdnUrl(saveKey, cdnHost);
    }

    @Override
    public String getCdnHost() {
        return cdnHost;
    }

    //    public Optional<URL> put(final String saveKey, final InputStream input, final String contentType) {
    //        try {
    //            final ObjectMetadata metadata = new ObjectMetadata();
    //            metadata.addUserMetadata("Content-Type", contentType);
    //            Date expire = new Date(System.currentTimeMillis() + 60000L);
    //            metadata.setExpirationTime(expire);
    //            final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, saveKey, input, metadata);
    //            ossClient.putObject(putObjectRequest);
    //
    //            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, saveKey);
    //            urlRequest.setExpiration(expire);
    //            return Optional.of(ossClient.generatePresignedUrl(urlRequest));
    //        } catch (ClientException e) {
    //            logger.warn("ali put [{}] fail! Cause : {}", saveKey, ExceptionUtils.getStackTrace(e));
    //            return Optional.empty();
    //        } finally {
    //            try {
    //                input.close();
    //            } catch (IOException e) {
    //            }
    //        }
    //    }
    //
    //    public Optional<URL> put(final String saveKey, final File file, final String contentType) {
    //        try {
    //            Date expire = new Date(System.currentTimeMillis() + 60000L);
    //            final ObjectMetadata metadata = new ObjectMetadata();
    //            metadata.setExpirationTime(expire);
    //            metadata.setContentType(contentType);
    //            final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, saveKey, file);
    //            putObjectRequest.setMetadata(metadata);
    //            ossClient.putObject(putObjectRequest);
    //            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, saveKey);
    //            urlRequest.setExpiration(expire);
    //            return Optional.of(ossClient.generatePresignedUrl(urlRequest));
    //        } catch (Exception e) {
    //            logger.warn("ali put [{}] fail! Cause : {}", saveKey, e.getMessage());
    //            return Optional.empty();
    //        }
    //    }

    @Override
    public Optional<URL> upload(String saveKey, File file, InputStream input, String contentType) {
        try {
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.addUserMetadata("Content-Type", contentType);
 
            final PutObjectRequest putObjectRequest;
            if (input != null) {
                putObjectRequest = new PutObjectRequest(bucketName, saveKey, input, metadata);
            } else {
                putObjectRequest = new PutObjectRequest(bucketName, saveKey, file);
                putObjectRequest.setMetadata(metadata);
            }
            ossClient.putObject(putObjectRequest);

            return this.getCdnUrl(saveKey);
        } catch (ClientException e) {
            logger.warn("ali put [{}] fail! Cause : {}", saveKey, ExceptionUtils.getStackTrace(e));
            return Optional.empty();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public boolean delete(final String saveKey) {
        try {
            ossClient.deleteObject(bucketName, saveKey);
            return true;
        } catch (RuntimeException e) {
            logger.warn("ali delete [{}] fail! Cause : {}", saveKey, e.getMessage());
            return false;
        }
    }

}
