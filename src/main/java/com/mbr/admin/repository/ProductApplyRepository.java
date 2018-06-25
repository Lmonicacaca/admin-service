package com.mbr.admin.repository;

import com.mbr.admin.domain.app.ProductApply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductApplyRepository extends MongoRepository<ProductApply,Long> {

    @Override
    <S extends ProductApply> S save(S s);
}
