package com.mbr.admin.domain.app;

import java.util.Date;

public class Notification {
    private Long id;
    private String deviceId;// 设备ID
    private String pushId;// 推送ID
//    private String content;// 内容
    private int type;// 通知类型

    private String title;
    private Date createTime;
    private int isRead; // 是否已读
    private Long transactionId;
    private Long payBillId; // 支付表iD
    private Date updateTime;
    private Integer transfer;// 0 Received 转入 1 Sent 转出 2 withdraw 提现 3 Payment 支付
    private Long channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

   /* public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }*/

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getPayBillId() {
        return payBillId;
    }

    public void setPayBillId(Long payBillId) {
        this.payBillId = payBillId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", pushId='" + pushId + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", isRead=" + isRead +
                ", transactionId=" + transactionId +
                ", payBillId=" + payBillId +
                ", updateTime=" + updateTime +
                ", transfer=" + transfer +
                ", channel=" + channel +
                '}';
    }
}
