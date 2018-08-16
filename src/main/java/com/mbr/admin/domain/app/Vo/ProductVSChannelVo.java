package com.mbr.admin.domain.app.Vo;

import java.util.Date;

public class ProductVSChannelVo {
    private Long id;
    private Long channel;
    private Long productId;
    private int onlineStatus;
    private int orderNo;
    private int isForceShow;
    private boolean merchantShow;
    private Date createTime;
    private Long oldChannel;
    private Long oldProductId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
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

    public boolean getMerchantShow() {
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

    public Long getOldChannel() {
        return oldChannel;
    }

    public void setOldChannel(Long oldChannel) {
        this.oldChannel = oldChannel;
    }

    public Long getOldProductId() {
        return oldProductId;
    }

    public void setOldProductId(Long oldProductId) {
        this.oldProductId = oldProductId;
    }

    @Override
    public String toString() {
        return "ProductVSChannelVo{" +
                "id=" + id +
                ", channel=" + channel +
                ", productId=" + productId +
                ", onlineStatus=" + onlineStatus +
                ", orderNo=" + orderNo +
                ", isForceShow=" + isForceShow +
                ", merchantShow=" + merchantShow +
                ", createTime=" + createTime +
                ", oldChannel=" + oldChannel +
                ", oldProductId=" + oldProductId +
                '}';
    }
}
