package com.mbr.admin.feign.dto.product;

import java.util.Date;

public class Product {

    private Long id;
    private Date createTime;
    private Date updateTime;

    /**币名称**/
    private String  coinName;
    /**币种类型，0是主链币，1是代币**/
    private Long coinType;
    /**币logi**/
    private String  coinAvatarUrl;
    /**币描述**/
    private String  coinDescription;
    /**是否erc20  1 是代币，0 是标准**/
    private Integer  coinErc20;
    /**是否允许交易 0 是 1 否**/
    private int  onlineStatus;
    /**链类型**/
    private String  chainType;
    /**链地址**/
    private String  tokenAddress;
    /**币精度**/
    private Integer  coinDecimals;

    private Integer gasLimit;


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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public Long getCoinType() {
        return coinType;
    }

    public void setCoinType(Long coinType) {
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

    public Integer getCoinErc20() {
        return coinErc20;
    }

    public void setCoinErc20(Integer coinErc20) {
        this.coinErc20 = coinErc20;
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

    public Integer getCoinDecimals() {
        return coinDecimals;
    }

    public void setCoinDecimals(Integer coinDecimals) {
        this.coinDecimals = coinDecimals;
    }

    public Integer getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(Integer gasLimit) {
        this.gasLimit = gasLimit;
    }
}
