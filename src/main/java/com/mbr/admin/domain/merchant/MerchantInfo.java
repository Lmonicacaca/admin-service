package com.mbr.admin.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "merchant_info")
public class MerchantInfo {
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "logo_bill")
    private String logoBill;
    @Column(name = "description")
    private String description;
    @Column(name = "website")
    private String website;
    @Column(name = "address")
    private String address;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_time")
    private String updateTime;
    @Column(name = "create_user_name")
    private String createUserName;
    @Column(name = "update_user_name")
    private String updateUserName;
    @Column(name = "status")
    private int status;
    @Column(name = "logo_intro")
    private String logoIntro;
    @Column(name = "rsa_public")
    private String rsaPublic;
    @Column(name = "rsa_private")
    private String rsaPrivate;
    @Column(name = "is_show")
    private int isShow;
    @Column(name = "channel")
    private Long channel;
    @Column(name = "audit")
    private Integer audit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoBill() {
        return logoBill;
    }

    public void setLogoBill(String logoBill) {
        this.logoBill = logoBill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    @Override
    public String toString() {
        return "MerchantInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", logoBill='" + logoBill + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createUserName='" + createUserName + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", status=" + status +
                ", logoIntro='" + logoIntro + '\'' +
                ", rsaPublic='" + rsaPublic + '\'' +
                ", rsaPrivate='" + rsaPrivate + '\'' +
                ", isShow=" + isShow +
                ", channel='" + channel + '\'' +
                ", audit=" + audit +
                '}';
    }
}
