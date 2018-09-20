package com.mbr.admin.domain.app;

import lombok.Data;

import java.util.Date;

@Data
public class AppUpdate {
    private Long id;
    private String version;
    private String url;
    private boolean force;
    private String content;
    private Date createTime;
    private Date updateTime;
    private String appUpdateType;
    private String iosLogo;
    private Long channel;
    private String plistUrl;
    private String appUpdateBuild;
    private long size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAppUpdateType() {
        return appUpdateType;
    }

    public void setAppUpdateType(String appUpdateType) {
        this.appUpdateType = appUpdateType;
    }

    public String getIosLogo() {
        return iosLogo;
    }

    public void setIosLogo(String iosLogo) {
        this.iosLogo = iosLogo;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public String getPlistUrl() {
        return plistUrl;
    }

    public void setPlistUrl(String plistUrl) {
        this.plistUrl = plistUrl;
    }

    public String getAppUpdateBuild() {
        return appUpdateBuild;
    }

    public void setAppUpdateBuild(String appUpdateBuild) {
        this.appUpdateBuild = appUpdateBuild;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "AppUpdate{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", force=" + force +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", appUpdateType='" + appUpdateType + '\'' +
                ", iosLogo='" + iosLogo + '\'' +
                ", channel=" + channel +
                ", plistUrl='" + plistUrl + '\'' +
                ", appUpdateBuild='" + appUpdateBuild + '\'' +
                ", size=" + size +
                '}';
    }
}
