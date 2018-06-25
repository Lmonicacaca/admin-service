package com.mbr.admin.repository;

import com.mbr.admin.domain.merchant.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,Long> {


    List<Product> findAllByOnlineStatus(int onlineStatus);

    Product findById(Long id);

    Product findByCoinNameAndTokenAddress(String coinName,String tokenAddress);
}
