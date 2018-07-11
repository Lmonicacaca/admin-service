package com.mbr.admin.domain.merchant.Vo;

import java.util.Date;

public class MerchantInfoVo {
    private Long id;
    private String name;
    private String channel;
    private String website;
    private String rsaPublic;
    private String rsaPrivate;
    private Integer isShow;
    private String oldImg;
    private String description;
    private String address;
    private String createUserName;
    private Date createTime;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
                ", createTime=" + createTime +
                '}';
    }
}
