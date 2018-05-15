package com.mbr.admin.common.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public abstract class BaseEntity implements java.io.Serializable{

    @Id
    private Long id; //ID
    @Column(name = "create_user_name")
    private String createUserName; //创建者
    @Column(name = "create_time")
    private Date createTime; //创建时间
    @Column(name = "update_user_name")
    private String updateUserName; //更新者
    @Column(name = "update_time")
    private Date updateTime ;//更新时间
    @Column(name = "status")
    private Integer status;//数据状态 0，有效，1 删除





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
}
