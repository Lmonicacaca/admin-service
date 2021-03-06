package com.mbr.admin.domain.merchant;

import lombok.Data;

import java.util.Date;

@Data
public class Channel {
    private Long id;
    private String systemName;
    private String merchantId;
    private Date createTime;
    private Long channel;
    private int status;
    private String appName;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", systemName='" + systemName + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", createTime=" + createTime +
                ", channel=" + channel +
                ", status=" + status +
                ", appName='" + appName + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
