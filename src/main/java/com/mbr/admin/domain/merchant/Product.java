package com.mbr.admin.domain.merchant;

import lombok.Data;

import java.util.Date;

@Data
public class Product {
    private Long id;
    private Date createTime;
    private String coinName;
    private int  coinType;
    private String coinAvatarUrl;
    private String coinDescription;
    private int onlineStatus;
    private String chainType;
    private String tokenAddress;
    private int coinDecimals;
    private int orderNo;
    private int isForceShow;
    private int coinErc20;
    private boolean merchantShow;
    private String gasLimit;
    private boolean def;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public int getCoinType() {
        return coinType;
    }

    public void setCoinType(int coinType) {
        this.coinType = coinType;
    }

    public String getCoinAvatarUrl() {
        return coinAvatarUrl;
    }

    public void setCoinAvatarUrl(String coinAvatarUrl) {
        this.coinAvatarUrl = coinAvatarUrl;
    }

    public String getCoinDescription() {
        return coinDescription;
    }

    public void setCoinDescription(String coinDescription) {
        this.coinDescription = coinDescription;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getChainType() {
        return chainType;
    }

    public void setChainType(String chainType) {
        this.chainType = chainType;
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

    public int getCoinErc20() {
        return coinErc20;
    }

    public void setCoinErc20(int coinErc20) {
        this.coinErc20 = coinErc20;
    }

    public boolean isMerchantShow() {
        return merchantShow;
    }

    public void setMerchantShow(boolean merchantShow) {
        this.merchantShow = merchantShow;
    }

    public String getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }
}
