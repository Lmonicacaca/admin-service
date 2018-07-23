package com.mbr.admin.domain.app;

import lombok.Data;

import java.util.Date;

@Data
public class PrivacyPolicyAndAbout {
    private Long id;
    private String content;
    private Date createTime;
    private  int type;
    private Long channel;
    private String language;
    private String system;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "PrivacyPolicyAndAbout{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", type=" + type +
                ", channel='" + channel + '\'' +
                ", language='" + language + '\'' +
                ", system='" + system + '\'' +
                '}';
    }
}
