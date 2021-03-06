package com.mbr.admin.repository;

import com.mbr.admin.domain.app.AppUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUpdateRepository extends MongoRepository<AppUpdate,Long> {
    AppUpdate findByChannelAndAppUpdateTypeAndAppUpdateBuildAndAndVersion(Long channel,String appUpdateType,String appUpdateBuild,String version);
}
