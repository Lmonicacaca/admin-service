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

   /* //根据url和状态查找所有的banner
    public List<Banner> find(Query query, Class clazz);*/

    //根据ID删除广告信息
    @Override
    public void delete(Long id);


    //根据ID查询banner
    public Banner queryById(Long id);

    //查找当前的总记录条数
    @Override
    long count();

    //保存或修改
    @Override
    <S extends Banner> S save(S s);
}
