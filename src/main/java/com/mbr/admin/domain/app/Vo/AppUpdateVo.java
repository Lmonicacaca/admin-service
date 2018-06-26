package com.mbr.admin.domain.app.Vo;

import java.util.Date;

public class AppUpdateVo {
    private Long id;
    private Date createTime;
    private String oldImg;
    private String oldUrl;
    private String oldPlistUrl;
    private String channel;
    private String appUpdateType;
    private String url;
    private String plistUrl;
    private String version;
    private String content;
    private boolean force;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOldImg() {
        return oldImg;
    }

    public void setOldImg(String oldImg) {
        this.oldImg = oldImg;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAppUpdateType() {
        return appUpdateType;
    }

    public void setAppUpdateType(String appUpdateType) {
        this.appUpdateType = appUpdateType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlistUrl() {
        return plistUrl;
    }

    public void setPlistUrl(String plistUrl) {
        this.plistUrl = plistUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public String getOldUrl() {
        return oldUrl;
    }

    public void setOldUrl(String oldUrl) {
        this.oldUrl = oldUrl;
    }

    public String getOldPlistUrl() {
        return oldPlistUrl;
    }

    public void setOldPlistUrl(String oldPlistUrl) {
        this.oldPlistUrl = oldPlistUrl;
    }

    public boolean isForce() {
        return force;
    }

    @Override
    public String toString() {
        return "AppUpdateVo{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", oldImg='" + oldImg + '\'' +
                ", oldUrl='" + oldUrl + '\'' +
                ", oldPlistUrl='" + oldPlistUrl + '\'' +
                ", channel='" + channel + '\'' +
                ", appUpdateType='" + appUpdateType + '\'' +
                ", url='" + url + '\'' +
                ", plistUrl='" + plistUrl + '\'' +
                ", version='" + version + '\'' +
                ", content='" + content + '\'' +
                ", force=" + force +
                '}';
    }
}
