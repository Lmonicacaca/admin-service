package com.mbr.admin.domain.merchant;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "withdraw")
public class WithDraw {
    @Column(name = "id")
    private Long id;
    @Column(name = "merchant_id")
    private String merchantId;
    @Column(name = "coin_id")
    private Long coinId;
    @Column(name = "channel")
    private Long channel;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_time")
    private String updateTime;
    @Column(name = "address")
    private String address;
    @Column(name = "nonce")
    private  String nonce;
    @Column(name = "status")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WithDraw{" +
                "id=" + id +
                ", merchantId=" + merchantId +
                ", coinId=" + coinId +
                ", channel=" + channel +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", address='" + address + '\'' +
                ", nonce='" + nonce + '\'' +
                ", status=" + status +
                '}';
    }
}
