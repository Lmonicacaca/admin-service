package com.mbr.admin.domain.app;

import lombok.Data;


@Data
public class Help {
    private Long id;
    private Long typeId;
    private String content;
    private String title;
    private String language;
    private String createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Help{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
