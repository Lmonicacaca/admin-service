package com.mbr.admin.domain.merchant.Vo;

import java.math.BigDecimal;
import java.util.Date;

public class PayBillVo {
    private  Long id;
    private  Long merchantId;
    private  String refBizNo;
    private  Long coinId;
    private BigDecimal amount;
    private String goodsType;
    private String goodsTag;
    private String attach;
    private String toAddr;
    private String singedTx;
    private String txData;
    private int status;
    private String tokenAddr;
    private String txHash;
    private String fromAddr;
    private int billType;
    private Date createTime;
    private Date lastUpdateTime;
    private String industry;
    private String merchantName;
    private String gethMessage;
    private Long channel;
    private String coinName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getRefBizNo() {
        return refBizNo;
    }

    public void setRefBizNo(String refBizNo) {
        this.refBizNo = refBizNo;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getSingedTx() {
        return singedTx;
    }

    public void setSingedTx(String singedTx) {
        this.singedTx = singedTx;
    }

    public String getTxData() {
        return txData;
    }

    public void setTxData(String txData) {
        this.txData = txData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTokenAddr() {
        return tokenAddr;
    }

    public void setTokenAddr(String tokenAddr) {
        this.tokenAddr = tokenAddr;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getGethMessage() {
        return gethMessage;
    }

    public void setGethMessage(String gethMessage) {
        this.gethMessage = gethMessage;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    @Override
    public String toString() {
        return "PayBillVo{" +
                "id=" + id +
                ", merchantId=" + merchantId +
                ", refBizNo='" + refBizNo + '\'' +
                ", coinId=" + coinId +
                ", amount=" + amount +
                ", goodsType='" + goodsType + '\'' +
                ", goodsTag='" + goodsTag + '\'' +
                ", attach='" + attach + '\'' +
                ", toAddr='" + toAddr + '\'' +
                ", singedTx='" + singedTx + '\'' +
                ", txData='" + txData + '\'' +
                ", status=" + status +
                ", tokenAddr='" + tokenAddr + '\'' +
                ", txHash='" + txHash + '\'' +
                ", fromAddr='" + fromAddr + '\'' +
                ", billType=" + billType +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", industry='" + industry + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", gethMessage='" + gethMessage + '\'' +
                ", channel=" + channel +
                ", coinName='" + coinName + '\'' +
                '}';
    }
}
