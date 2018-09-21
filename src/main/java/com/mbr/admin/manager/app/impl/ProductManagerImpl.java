package com.mbr.admin.manager.app.impl;

import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.manager.app.ProductManager;
import com.mbr.admin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductManagerImpl implements ProductManager {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Map<String, Object>> findAllId() {
        List<Product> productList = productRepository.findAllByOnlineStatus(0);
        List<Map<String,Object>> list =  new ArrayList<>();
        for(int i=0;i<productList.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",productList.get(i).getId());
            map.put("text",productList.get(i).getCoinName());
            list.add(map);
        }
        return list;
    }
}
