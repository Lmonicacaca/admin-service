package com.mbr.admin.repository;

import com.mbr.admin.domain.merchant.Channel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChannelRepository extends MongoRepository<Channel,Long>{

    public List<Channel> findAllByStatus(int status);

//    public List<Channel> fin();

}
