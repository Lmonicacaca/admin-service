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

    private Integer orderNo;//排序
    private Integer isForceShow; //是否强制显示 0 显示 1 非显示
    private boolean merchantShow;//是否显示商家配送界面

    private boolean def;//是否为默认币种
    private String gasLimit;
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

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getIsForceShow() {
        return isForceShow;
    }

    public void setIsForceShow(Integer isForceShow) {
        this.isForceShow = isForceShow;
    }

    public boolean isMerchantShow() {
        return merchantShow;
    }

    public void setMerchantShow(boolean merchantShow) {
        this.merchantShow = merchantShow;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public String getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", coinName='" + coinName + '\'' +
                ", coinType=" + coinType +
                ", coinAvatarUrl='" + coinAvatarUrl + '\'' +
                ", coinDescription='" + coinDescription + '\'' +
                ", coinErc20=" + coinErc20 +
                ", onlineStatus=" + onlineStatus +
                ", chainType='" + chainType + '\'' +
                ", tokenAddress='" + tokenAddress + '\'' +
                ", coinDecimals=" + coinDecimals +
                ", orderNo=" + orderNo +
                ", isForceShow=" + isForceShow +
                ", merchantShow=" + merchantShow +
                ", def=" + def +
                ", gasLimit='" + gasLimit + '\'' +
                '}';
    }
}
