package com.mbr.admin.repository;

import com.mbr.admin.domain.app.Banner;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BannerRepository extends MongoRepository<Banner,Long> {

}
