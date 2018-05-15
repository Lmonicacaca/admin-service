package com.mbr.admin.dto;

import java.io.Serializable;

public class HelpMasterSaveDto implements Serializable{
    private String id;
    private String name;
    private int type;//1，帮助中心 2 常见问题

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
