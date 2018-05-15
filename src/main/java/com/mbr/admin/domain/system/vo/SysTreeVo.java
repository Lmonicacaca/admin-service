package com.mbr.admin.domain.system.vo;


import java.util.List;
import java.util.Map;

public class SysTreeVo implements java.io.Serializable {

    private Long id;
    private String text;
    private List<SysTreeVo> children;

    private Map<String,Boolean> state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SysTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysTreeVo> children) {
        this.children = children;
    }

    public Map<String, Boolean> getState() {
        return state;
    }

    public void setState(Map<String, Boolean> state) {
        this.state = state;
    }
}
