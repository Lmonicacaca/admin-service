package com.mbr.admin.domain.merchant.Vo;

import java.util.Date;

public class MerchantVsResourceVo {
    private Long id;
    private String merchantId;
    private String resourceId;
    private Integer status;
    private String createTime;
    private String updateTime;
    private String createUserName;
    private String updateUserName;
    private String channel;
    private String url;
    private String resourceIdList;
    private String merchantName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(String resourceIdList) {
        this.resourceIdList = resourceIdList;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "MerchantVsResourceVo{" +
                "id=" + id +
                ", merchantId='" + merchantId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", channel='" + channel + '\'' +
                ", url='" + url + '\'' +
                ", resourceIdList='" + resourceIdList + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
