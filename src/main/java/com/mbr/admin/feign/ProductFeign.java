package com.mbr.admin.feign;

import com.mbr.admin.common.domain.vo.PageResult;
import com.mbr.admin.common.dto.BaseResult;
import com.mbr.admin.feign.dto.product.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "product-service",url = "http://47.100.47.200:9904")
public interface ProductFeign {

    @PostMapping(value = "queryByAdminForPage")
    BaseResult<PageResult<Product>> queryByAdminForPage(@RequestParam(value = "coinName",required = false) String coinName,
                                                        @RequestParam(value = "pageNo") int pageNo,
                                                        @RequestParam(value = "pageSize") int pageSize);

    @DeleteMapping(value = "deleteById/{id}")
    BaseResult deleteById(@PathVariable(value = "id") Long id);

    @PostMapping(value = "queryById/{id}")
    BaseResult<Product> queryById(@PathVariable(value = "id") Long id);

    @PostMapping(value = "save")
    BaseResult save(@RequestBody Product p);
}
