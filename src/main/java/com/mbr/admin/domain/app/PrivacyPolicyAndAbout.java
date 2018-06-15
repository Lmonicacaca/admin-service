package com.mbr.admin.domain.app;

import lombok.Data;

@Data
public class PrivacyPolicyAndAbout {
    private Long id;
    private String content;
    private String createTime;
    private  int type;
    private String channel;
    private String language;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "PrivacyPolicyAndAbout{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", type=" + type +
                ", channel='" + channel + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
