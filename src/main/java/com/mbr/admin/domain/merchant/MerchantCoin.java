package com.mbr.admin.domain.merchant;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name="merchant_coin")
public class MerchantCoin {
    @Column(name = "id")
    private Long id;
    @Column(name = "coin_id")
    private long coinId;
    @Column(name = "merchant_id")
    private String merchantId;
    @Column(name = "address")
    private String address;
    @Column(name = "token_address")
    private String tokenAddress;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "channel")
    private Long channel;
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_user_name")
    private String createUserName; //创建者
    @Column(name = "create_time")
    private String createTime; //创建时间
    @Column(name = "update_user_name")
    private String updateUserName; //更新者
    @Column(name = "update_time")
    private String updateTime ;//更新时间



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

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "MerchantCoin{" +
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
                '}';
    }
}
