package com.mbr.admin.domain.merchant;

import lombok.Data;

import javax.persistence.Column;

@Data
public class MerchantHelpCategory {
    @Column(name = "id")
    private Integer id;
    @Column(name = "lang")
    private String lang;
    @Column(name = "name")
    private String name;
    @Column(name = "is_top")
    private boolean isTop;
    @Column(name = "is_show")
    private boolean isShow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "MerchantHelpCategory{" +
                "id=" + id +
                ", lang='" + lang + '\'' +
                ", name='" + name + '\'' +
                ", isTop=" + isTop +
                ", isShow=" + isShow +
                '}';
    }
}
