package com.mbr.admin.domain.app.Vo;

public class BannerVo {
    private Long id;
    private int orderBy;
    private String  url;
//    private String file;
    private int type;
    private String channel;
    private String simage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSimage() {
        return simage;
    }

    public void setSimage(String simage) {
        this.simage = simage;
    }

    @Override
    public String toString() {
        return "BannerVo{" +
                "id='" + id + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", channel='" + channel + '\'' +
                ", simage='" + simage + '\'' +
                '}';
    }
}
