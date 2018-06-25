package com.mbr.admin.repository;

import com.mbr.admin.domain.app.Help;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HelpRepository extends MongoRepository<Help,Long> {
}
