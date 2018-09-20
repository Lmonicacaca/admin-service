package com.mbr.admin.domain.app.Vo;

import java.util.Date;

public class AppUpdateVo {
    private Long id;
    private String createTime;
    private String updateTime;
    private String channel;
    private String appUpdateType;
    private String version;
    private String content;
    private boolean force;
    private String appUpdateBuild;
    private String oldUrl;
    private String oldPlistUrl;
    private String oldIosLogo;
    private long size;
    private long oldSize;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public boolean isForce() {
        return force;
    }

    public String getAppUpdateBuild() {
        return appUpdateBuild;
    }

    public void setAppUpdateBuild(String appUpdateBuild) {
        this.appUpdateBuild = appUpdateBuild;
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

    public String getOldIosLogo() {
        return oldIosLogo;
    }

    public void setOldIosLogo(String oldIosLogo) {
        this.oldIosLogo = oldIosLogo;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getOldSize() {
        return oldSize;
    }

    public void setOldSize(long oldSize) {
        this.oldSize = oldSize;
    }

    @Override
    public String toString() {
        return "AppUpdateVo{" +
                "id=" + id +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", channel='" + channel + '\'' +
                ", appUpdateType='" + appUpdateType + '\'' +
                ", version='" + version + '\'' +
                ", content='" + content + '\'' +
                ", force=" + force +
                ", appUpdateBuild='" + appUpdateBuild + '\'' +
                ", oldUrl='" + oldUrl + '\'' +
                ", oldPlistUrl='" + oldPlistUrl + '\'' +
                ", oldIosLogo='" + oldIosLogo + '\'' +
                ", size=" + size +
                ", oldSize=" + oldSize +
                '}';
    }
}
