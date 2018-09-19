package com.mbr.admin.common.storage;

import com.UpYun;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:CDN配置
 *
 * @outhor liuji
 * @create 2018-06-25 14:24
 */
@Configuration
public class CdnConfig {
    private static final Logger logger = LoggerFactory.getLogger(CdnConfig.class);
    // aws
    @Value("${amazon.aws.region}")
    private String region;
    @Value("${amazon.aws.accessKey}")
    private String accessKey;
    @Value("${amazon.aws.secretKey}")
    private String secretKey;
    @Value("${amazon.s3.bucketName}")
    private String bucketName;
    @Value("${amazon.cdn.host}")
    private String awsCdnHost;
    // aliyun
    @Value("${aliyun.cdn.endpoint}")
    private String endPoint;
    @Value("${aliyun.cdn.accesskeyid}")
    private String accessKeyId;
    @Value("${aliyun.cdn.accesskeysecret}")
    private String accessKeySecret;
    @Value("${aliyun.cdn.bucket}")
    private String aliBacket;
    @Value("${aliyun.cdn.host}")
    private String aliCdnHost;
    // upyun
    @Value("${upyun.cdn.endpoint}")
    private String apiDomain;
    @Value("${upyun.cdn.savePath}")
    private String upyunSavePath;
    @Value("${upyun.cdn.accessKeyId}")
    private String upyunAccessKeyId;
    @Value("${upyun.cdn.accessKeySecret}")
    private String upyunAccessKeySecret;
    @Value("${upyun.cdn.bucketName}")
    private String upyunBucket;
    @Value("${upyun.cdn.host}")
    private String upyunCdnHost;
    @Value("${upyun.cdn.timeout}")
    private int timeout;
    @Value("${local.cdn.root}")
    private String localCdnRoot;
    @Value("${local.cdn.host}")
    private String localCdnHost;

    // @Bean
    // public AmazonS3Client amazonS3Client() {
    // Region region = Region.getRegion(Regions.fromName(this.region));
    // AmazonS3Client amazonS3Client = new AmazonS3Client(new
    // BasicAWSCredentials(accessKey, secretKey));
    // amazonS3Client.setRegion(region);
    // return amazonS3Client;
    // }

    // @Bean
    // public DefaultAWSCredentialsProviderChain
    // defaultAWSCredentialsProviderChain() {
    // return new DefaultAWSCredentialsProviderChain();
    // }

    @Bean
    public OSSClient aliOSSClient() {
        return new OSSClient(endPoint, defaultCredentialProvider(), clientConfiguration());
    }

    @Bean
    public DefaultCredentialProvider defaultCredentialProvider() {
        return new DefaultCredentialProvider(accessKeyId, accessKeySecret);
    }

    @Bean
    public ClientConfiguration clientConfiguration() {
        return new ClientConfiguration();
    }

    // upyun

    @Bean
    public UpYun upYun() {
        UpYun upyun = new UpYun(upyunBucket, upyunAccessKeyId, upyunAccessKeySecret);
        upyun.setTimeout(timeout);
        upyun.setApiDomain(apiDomain);
        return upyun;
    }

    @Bean
    public CdnService cdnService(@Value("${system.cdn.mode}") String mo) {
        logger.info("当前CDN模式：{}", mo);
        CdnMode mode = CdnMode.fromValue(mo);
        switch (mode) {
        case ALIYUN:
            return new Aliyun(this.aliOSSClient(), aliBacket, aliCdnHost);
        // case AWS:
        // return new Amazonaws(this.amazonS3Client(), bucketName, awsCdnHost);
        case UPYUN:
            return new Upyun(this.upYun(), upyunBucket, upyunSavePath, upyunCdnHost);
        default:
            return new LocalStorage(localCdnRoot, localCdnHost);
        }
    }
}
