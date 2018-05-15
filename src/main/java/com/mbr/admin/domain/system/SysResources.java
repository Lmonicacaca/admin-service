package com.mbr.admin.domain.system;



import com.mbr.admin.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Table(name = "sys_resources")
public class SysResources extends BaseEntity {

    @Column(name = "resource_name")
    private String resourceName;
    @Column(name = "resource_desc")
    private String resourceDesc;
    @Column(name = "resource_path")
    private String resourcePath;
    @Column(name = "resource_parent")
    private Long resourceParent;
    @Column(name = "enable")
    private Integer enable;
    @Column(name = "resource_icon")
    private String resourceIcon;
    @Column(name = "order_no")
    private Integer orderNo;
    @Column(name = "resource_level")
    private Integer resourceLevel;


    @Transient
    private String parentName;
    @Transient
    private List<SysResources> children=new ArrayList<>();
    @Transient
    private String title;
    @Transient
    private boolean expanded;
    @Transient
    private boolean folder;

    @Transient
    private Long ResourceParentUpdate;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Long getResourceParent() {
        return resourceParent;
    }

    public void setResourceParent(Long resourceParent) {
        this.resourceParent = resourceParent;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getResourceLevel() {
        return resourceLevel;
    }

    public void setResourceLevel(Integer resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<SysResources> getChildren() {
        return children;
    }

    public void setChildren(List<SysResources> children) {
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public Long getResourceParentUpdate() {
        return ResourceParentUpdate;
    }

    public void setResourceParentUpdate(Long resourceParentUpdate) {
        ResourceParentUpdate = resourceParentUpdate;
    }
}
