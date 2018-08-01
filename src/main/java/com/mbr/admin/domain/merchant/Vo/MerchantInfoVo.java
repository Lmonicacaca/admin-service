package com.mbr.admin.domain.merchant.Vo;

import java.util.Date;

public class MerchantInfoVo {
    private Long id;
    private String name;
    private Long channel;
    private String website;
    private String rsaPublic;
    private String rsaPrivate;
    private Integer isShow;
    private String oldImg;
    private String description;
    private String address;
    private String createUserName;
    private String createTime;
    private String logoBill;
    private String updateTime;
    private String updateUserName;
    private int status;
    private String logoIntro;
    private Integer audit;
    private String appName;
    private Long oldChannel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getRsaPublic() {
        return rsaPublic;
    }

    public void setRsaPublic(String rsaPublic) {
        this.rsaPublic = rsaPublic;
    }

    public String getRsaPrivate() {
        return rsaPrivate;
    }

    public void setRsaPrivate(String rsaPrivate) {
        this.rsaPrivate = rsaPrivate;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }


    public String getOldImg() {
        return oldImg;
    }

    public void setOldImg(String oldImg) {
        this.oldImg = oldImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLogoBill() {
        return logoBill;
    }

    public void setLogoBill(String logoBill) {
        this.logoBill = logoBill;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogoIntro() {
        return logoIntro;
    }

    public void setLogoIntro(String logoIntro) {
        this.logoIntro = logoIntro;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getOldChannel() {
        return oldChannel;
    }

    public void setOldChannel(Long oldChannel) {
        this.oldChannel = oldChannel;
    }

    @Override
    public String toString() {
        return "MerchantInfoVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", channel='" + channel + '\'' +
                ", website='" + website + '\'' +
                ", rsaPublic='" + rsaPublic + '\'' +
                ", rsaPrivate='" + rsaPrivate + '\'' +
                ", isShow=" + isShow +
                ", oldImg='" + oldImg + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", logoBill='" + logoBill + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", status=" + status +
                ", logoIntro='" + logoIntro + '\'' +
                ", audit=" + audit +
                ", appName='" + appName + '\'' +
                ", oldChannel=" + oldChannel +
                '}';
    }
}
