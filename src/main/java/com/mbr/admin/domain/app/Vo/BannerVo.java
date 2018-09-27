package com.mbr.admin.domain.app.Vo;

import lombok.Data;

@Data
public class BannerVo {
    private Long id;
    private String orderBy;
    private String  url;
    private String type;
    private String channel;
    private String createTime;
    private String status;
    private String oldImage;
    private String oldType;
    private String oldOrderBy;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOldImage() {
        return oldImage;
    }

    public void setOldImage(String oldImage) {
        this.oldImage = oldImage;
    }

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public String getOldOrderBy() {
        return oldOrderBy;
    }

    public void setOldOrderBy(String oldOrderBy) {
        this.oldOrderBy = oldOrderBy;
    }

    @Override
    public String toString() {
        return "BannerVo{" +
                "id=" + id +
                ", orderBy='" + orderBy + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", channel='" + channel + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status='" + status + '\'' +
                ", oldImage='" + oldImage + '\'' +
                ", oldType='" + oldType + '\'' +
                ", oldOrderBy='" + oldOrderBy + '\'' +
                '}';
    }
}
