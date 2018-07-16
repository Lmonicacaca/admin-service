package com.mbr.admin.domain.merchant.Vo;

import java.util.Date;

public class WithDrawVo {
    private Long id;
    private String merchantId;
    private Long coinId;
    private String channel;
    private String createTime;
    private String updateTime;
    private String address;
    private  String nonce;
    private Integer status;
    private String coinName;
    private String merchantName;
    private String tokenAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    @Override
    public String toString() {
        return "WithDrawVo{" +
                "id=" + id +
                ", merchantId='" + merchantId + '\'' +
                ", coinId=" + coinId +
                ", channel='" + channel + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", address='" + address + '\'' +
                ", nonce='" + nonce + '\'' +
                ", status=" + status +
                ", coinName='" + coinName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", tokenAddress='" + tokenAddress + '\'' +
                '}';
    }
}
