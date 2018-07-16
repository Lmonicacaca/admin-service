package com.mbr.admin.domain.merchant;


import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_notice")
public class MerchantNotice {
    @Column(name = "id")
    private Integer id;
    @Column(name = "lang")
    private String lang;
    @Column(name = "notice_type")
    private Integer noticeType;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "notice)to")
    private String noticeTo;
    @Column(name = "create_user")
    private Integer createUser;
    @Column(name = "create_user_name")
    private String createUserName;
}
