package com.mbr.admin.domain.app;

import lombok.Data;

import java.util.Date;

@Data
public class ProductApply {
    private Long id;
    private String coinName;
    private String coinDescription;
    private String tokenAddress;
    private int coinDecimals;
    private Long channel;
    private String merchantName;
    private int status;
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinDescription() {
        return coinDescription;
    }

    public void setCoinDescription(String coinDescription) {
        this.coinDescription = coinDescription;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public int getCoinDecimals() {
        return coinDecimals;
    }

    public void setCoinDecimals(int coinDecimals) {
        this.coinDecimals = coinDecimals;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    @Override
    public String toString() {
        return "ProductApply{" +
                "id='" + id + '\'' +
                ", coinName='" + coinName + '\'' +
                ", coinDescription='" + coinDescription + '\'' +
                ", tokenAddress='" + tokenAddress + '\'' +
                ", coinDecimals='" + coinDecimals + '\'' +
                ", channel='" + channel + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
