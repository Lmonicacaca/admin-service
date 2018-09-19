package com.mbr.admin.domain.app.Vo;

import java.util.Date;

public class AppUpdateVo {
    private Long id;
    private Date createTime;
    private String channel;
    private String appUpdateType;
    private String version;
    private String content;
    private boolean force;
    private String appUpdateBuild;


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

    @Override
    public String toString() {
        return "AppUpdateVo{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", channel='" + channel + '\'' +
                ", appUpdateType='" + appUpdateType + '\'' +
                ", version='" + version + '\'' +
                ", content='" + content + '\'' +
                ", force=" + force +
                ", appUpdateBuild='" + appUpdateBuild + '\'' +
                '}';
    }
}
