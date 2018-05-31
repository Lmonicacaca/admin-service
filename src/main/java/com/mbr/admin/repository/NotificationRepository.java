package com.mbr.admin.repository;

import com.mbr.admin.domain.app.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,Long> {


}
