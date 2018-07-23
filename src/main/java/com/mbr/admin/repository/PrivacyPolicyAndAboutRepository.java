package com.mbr.admin.repository;

import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivacyPolicyAndAboutRepository extends MongoRepository<PrivacyPolicyAndAbout,Long> {

    public PrivacyPolicyAndAbout findById(Long id);
}
