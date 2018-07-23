package com.mbr.admin.repository;

import com.mbr.admin.domain.app.ProductVsChannel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductVsChannelRepository extends MongoRepository<ProductVsChannel,Long> {

    ProductVsChannel findByChannelAndProductId(Long channel,String productId);
}
