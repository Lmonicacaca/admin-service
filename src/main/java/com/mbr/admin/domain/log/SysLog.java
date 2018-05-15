package com.mbr.admin.domain.log;



import com.mbr.admin.common.annotation.ExcelField;
import com.mbr.admin.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_log")
public class SysLog extends BaseEntity {

    @ExcelField("内容")
    @Column(name = "content")
    private String content;

    @ExcelField("用户名")
    @Column(name = "username")
    private String username;

    @ExcelField("类型")
    @Column(name = "type")
    private String type;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}