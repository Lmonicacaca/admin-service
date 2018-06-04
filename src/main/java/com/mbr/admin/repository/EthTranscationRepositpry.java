package com.mbr.admin.repository;

import com.mbr.admin.domain.app.EthTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EthTranscationRepositpry extends MongoRepository<EthTransaction,Long> {

}
