package com.mbr.admin.domain.merchant;

import com.mbr.admin.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="merchant_coin")
public class MerchantCoin  extends BaseEntity{
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
    private String channel;



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

    @Override
    public String toString() {
        return "MerchantCoin{" +
                "coinId=" + coinId +
                ", merchantId=" + merchantId +
                ", address='" + address + '\'' +
                ", tokenAddress='" + tokenAddress + '\'' +
                ", coinName='" + coinName + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
