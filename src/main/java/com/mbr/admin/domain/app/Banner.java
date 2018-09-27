package com.mbr.admin.domain.app;

import lombok.Data;

import java.util.Date;

@Data
public class Banner {
    private Long id;
    private String url;
    private String image;
    private int orderBy;
    private Date createTime;
    private int status;// 0 显示 1 不显示
    private int type;// 1 是余额，2 是 商家 3 支付
    private Long channel;//渠道号

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     *            the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the orderBy
     */
    public int getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy
     *            the orderBy to set
     */
    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", orderBy=" + orderBy +
                ", createTime=" + createTime +
                ", status=" + status +
                ", type=" + type +
                ", channel=" + channel +
                '}';
    }
}
