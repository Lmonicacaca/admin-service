package com.mbr.admin.domain.merchant.Vo;


public class ChannelVo {
    private Long id;
    private String systemName;
    private String merchantId;
    private String createTime;
    private Long channel;
    private int status;
    private String appName;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "ChannelVo{" +
                "id=" + id +
                ", systemName='" + systemName + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", channel=" + channel +
                ", status=" + status +
                ", appName='" + appName + '\'' +
                '}';
    }
}
