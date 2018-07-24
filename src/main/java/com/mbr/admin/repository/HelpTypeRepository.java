package com.mbr.admin.repository;

import com.mbr.admin.domain.app.HelpType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HelpTypeRepository extends MongoRepository<HelpType,Long> {

}
