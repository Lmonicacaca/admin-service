package com.mbr.admin.domain.merchant;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "merchant_coin_map")
public class MerchantCoinTmp {
    @Column(name = "id")
    private String id;
    @Column(name = "coin_id")
    private String coinId;
    @Column(name = "merchant_id")
    private String merchantId;
    @Column(name = "status")
    private int status;
    @Column(name = "create_user_name")
    private String createUserName;
    @Column(name = "update_user_name")
    private String updateUserName;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "recharge_address")
    private String rechargeAddress;
    @Column(name = "token_address")
    private String tokenAddress;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "channel")
    private String channel;
    @Column(name = "withdraw_address")
    private String withdrawAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getRechargeAddress() {
        return rechargeAddress;
    }

    public void setRechargeAddress(String rechargeAddress) {
        this.rechargeAddress = rechargeAddress;
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

    public String getWithdrawAddress() {
        return withdrawAddress;
    }

    public void setWithdrawAddress(String withdrawAddress) {
        this.withdrawAddress = withdrawAddress;
    }

    @Override
    public String toString() {
        return "MerchantCoinTmp{" +
                "id='" + id + '\'' +
                ", coinId='" + coinId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", status=" + status +
                ", createUserName='" + createUserName + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", rechargeAddress='" + rechargeAddress + '\'' +
                ", tokenAddress='" + tokenAddress + '\'' +
                ", coinName='" + coinName + '\'' +
                ", channel='" + channel + '\'' +
                ", withdrawAddress='" + withdrawAddress + '\'' +
                '}';
    }
}
