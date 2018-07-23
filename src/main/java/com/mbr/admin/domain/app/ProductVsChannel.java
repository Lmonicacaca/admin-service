package com.mbr.admin.domain.app;

import lombok.Data;

import java.util.Date;

@Data
public class ProductVsChannel {
    private String id;
    private Long channel;
    private String productId;
    private int onlineStatus;
    private int orderNo;
    private int isForceShow;
    private boolean merchantShow;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getIsForceShow() {
        return isForceShow;
    }

    public void setIsForceShow(int isForceShow) {
        this.isForceShow = isForceShow;
    }

    public boolean isMerchantShow() {
        return merchantShow;
    }

    public void setMerchantShow(boolean merchantShow) {
        this.merchantShow = merchantShow;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ProductVsChannel{" +
                "id='" + id + '\'' +
                ", channel='" + channel + '\'' +
                ", productId='" + productId + '\'' +
                ", onlineStatus=" + onlineStatus +
                ", orderNo=" + orderNo +
                ", isForceShow=" + isForceShow +
                ", merchantShow=" + merchantShow +
                ", createTime=" + createTime +
                '}';
    }
}
