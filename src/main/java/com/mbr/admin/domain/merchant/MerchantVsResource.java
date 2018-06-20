package com.mbr.admin.domain.merchant;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "merchant_vs_resource")
public class MerchantVsResource {
    @Column(name = "id")
    private String id;
    @Column(name = "merchant_id")
    private String merchantId;
    @Column(name = "resource_id")
    private String resourceId;
    @Column(name = "status")
    private int status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "create_user_name")
    private String createUserName;
    @Column(name = "update_user_name")
    private String updateUserName;
    @Column(name = "channel")
    private String channel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "MerchantVsResource{" +
                "id='" + id + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createUserName='" + createUserName + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
