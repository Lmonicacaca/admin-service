package com.mbr.admin.domain.merchant.Vo;

import java.util.Date;

public class MerchantCoinVo {
    private Long id;
    private long coinId;
    private String merchantId;
    private String address;
    private String tokenAddress;
    private String coinName;
    private String channel;
    private Integer status;
    private String createUserName; //创建者
    private String createTime; //创建时间
    private String updateUserName; //更新者
    private String updateTime ;//更新时间
    private String merchantName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCoinId() {
        return coinId;
    }

    public void setCoinId(long coinId) {
        this.coinId = coinId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "MerchantCoinVo{" +
                "id=" + id +
                ", coinId=" + coinId +
                ", merchantId='" + merchantId + '\'' +
                ", address='" + address + '\'' +
                ", tokenAddress='" + tokenAddress + '\'' +
                ", coinName='" + coinName + '\'' +
                ", channel='" + channel + '\'' +
                ", status=" + status +
                ", createUserName='" + createUserName + '\'' +
                ", createTime=" + createTime +
                ", updateUserName='" + updateUserName + '\'' +
                ", updateTime=" + updateTime +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
