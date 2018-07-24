package com.mbr.admin.domain.app;

import lombok.Data;

import java.util.Date;

@Data
public class HelpType {
    private Long id;
    private String name;
    private Date createTime;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
