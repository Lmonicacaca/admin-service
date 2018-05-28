package com.mbr.admin.domain.merchant;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name="pay_bill")
public class PayBill {
    @Column(name = "id")
    private  Long id;
    @Column(name = "merchant_id")
    private  Long merchantId;
    @Column(name = "ref_biz_no")
    private  String refBizNo;
    @Column(name = "coin_id")
    private  Long coinId;
    @Column(name = "amount")
    private  BigDecimal amount;
    @Column(name = "goods_type")
    private String goodsType;
    @Column(name = "goods_tag")
    private String goodsTag;
    @Column(name = "attach")
    private String attach;
    @Column(name = "to_addr")
    private String toAddr;
    @Column(name = "singed_tx")
    private String singedTx;
    @Column(name = "tx_data")
    private String txData;
    @Column(name = "status")
    private int status;
    @Column(name = "token_addr")
    private String tokenAddr;
    @Column(name = "tx_hash")
    private String txHash;
    @Column(name = "from_addr")
    private String fromAddr;
    @Column(name = "bill_type")
    private int billType;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "last_update_time")
    private Date lastUpdateTime;
    @Column(name = "industry")
    private String industry;
    @Column(name = "merchant_name")
    private String merchantName;
    @Column(name = "geth_message")
    private String gethMessage;
    @Column(name = "channel")
    private Long channel;


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

    @Override
    public String toString() {
        return "PayBill{" +
                "id='" + id + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", refBizNo='" + refBizNo + '\'' +
                ", coinId='" + coinId + '\'' +
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
                ", channel='" + channel + '\'' +
                '}';
    }
}
