package com.mbr.admin.domain.app;

import lombok.Data;

import java.util.Date;

@Data
public class EthTransaction {
    private String id;
    private String createTime;
    private String updateTime;
    private Integer status;
    private Integer txStatus;
    private String orderId;
    private Integer confirmations;
    private String fee;
    private String height;
    private boolean isErc20;
    private String tokenAddress;
    private Long coinId;
    private String hash;
    private Integer noce;
    private String blockHash;
    private String blockNumber;
    private Integer transactionIndex;
    private String from ;
    private String to;
    private String value;
    private String gasPrice;
    private String gas;
    private String input;
    private String r;
    private String s;
    private Integer v;
    private String channel;
    private String memo;
    private String signedTx;
    private Integer decimals;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTxStatus() {
        return txStatus;
    }

    public void setTxStatus(Integer txStatus) {
        this.txStatus = txStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public boolean isErc20() {
        return isErc20;
    }

    public void setErc20(boolean erc20) {
        isErc20 = erc20;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getNoce() {
        return noce;
    }

    public void setNoce(Integer noce) {
        this.noce = noce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Integer getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(Integer transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSignedTx() {
        return signedTx;
    }

    public void setSignedTx(String signedTx) {
        this.signedTx = signedTx;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    @Override
    public String toString() {
        return "EthTransaction{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                ", txStatus=" + txStatus +
                ", orderId='" + orderId + '\'' +
                ", confirmations=" + confirmations +
                ", fee='" + fee + '\'' +
                ", height='" + height + '\'' +
                ", isErc20=" + isErc20 +
                ", tokenAddress='" + tokenAddress + '\'' +
                ", coinId='" + coinId + '\'' +
                ", hash='" + hash + '\'' +
                ", noce=" + noce +
                ", blockHash='" + blockHash + '\'' +
                ", blockNumber='" + blockNumber + '\'' +
                ", transactionIndex=" + transactionIndex +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", value='" + value + '\'' +
                ", gasPrice='" + gasPrice + '\'' +
                ", gas='" + gas + '\'' +
                ", input='" + input + '\'' +
                ", r='" + r + '\'' +
                ", s='" + s + '\'' +
                ", v=" + v +
                ", channel='" + channel + '\'' +
                ", memo='" + memo + '\'' +
                ", signedTx='" + signedTx + '\'' +
                ", decimals=" + decimals +
                '}';
    }
}
