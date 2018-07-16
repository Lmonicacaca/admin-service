package com.mbr.admin.domain.merchant;


import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_help")
public class MerchantHelp {
    @Column(name = "id")
    private Long id;
    @Column(name = "cat_id")
    private Integer catId;
    @Column(name = "lang")
    private String lang;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "create_user")
    private Long createUser;
    @Column(name = "create_user_name")
    private String createUserName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public String toString() {
        return "MerchantHelp{" +
                "id=" + id +
                ", catId=" + catId +
                ", lang='" + lang + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", createUserName='" + createUserName + '\'' +
                '}';
    }
}
