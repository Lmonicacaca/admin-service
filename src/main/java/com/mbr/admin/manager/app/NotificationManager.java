package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.Notification;

import java.util.List;
import java.util.Map;

public interface NotificationManager {
    public List<Notification> queryList(int type,int transfer);

    public Notification queryById(Long id);

    public void updateById(Long id,String title);

    public List<Map<String,Object>> queryChannel();

    public List<Map<String,Object>> queryType();

    public List<Map<String,Object>> queryTransfer();

    public void insertNotification(Notification notification);
}
