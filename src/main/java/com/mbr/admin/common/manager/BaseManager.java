package com.mbr.admin.common.manager;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Luoqb on 2017/3/22.
 */
public interface BaseManager<T> {

    public List<T> queryAll(T t);


    public T queryOne(T t);


    public T queryById(Object t);

    @Transactional(rollbackFor=Exception.class)
    public int updateByPrimaryKeySelective(T t) throws Exception;

    @Transactional(rollbackFor=Exception.class)
    public int updateByPrimaryKey(T t) throws Exception;

    @Transactional(rollbackFor=Exception.class)
    public int delete(T t)throws Exception;

    @Transactional(rollbackFor=Exception.class)
    public int deleteById(Object t)throws Exception;

    @Transactional(rollbackFor=Exception.class)
    public int insert(T t) throws Exception;


    @Transactional(rollbackFor=Exception.class)
    public int insertSelective(T t) throws Exception;


}
