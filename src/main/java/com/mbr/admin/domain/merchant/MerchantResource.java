package com.mbr.admin.domain.merchant;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "merchant_resource")
public class MerchantResource {
    @Column(name = "id")
    private Long id;
    @Column(name = "url")
    private String url;
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "create_user_name")
    private String create_user_name;
    @Column(name = "updateUserName")
    private String update_user_name;
    @Column(name = "roleName")
    private String role_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCreate_user_name() {
        return create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name;
    }

    public String getUpdate_user_name() {
        return update_user_name;
    }

    public void setUpdate_user_name(String update_user_name) {
        this.update_user_name = update_user_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public String toString() {
        return "MerchantResource{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", create_user_name='" + create_user_name + '\'' +
                ", update_user_name='" + update_user_name + '\'' +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
