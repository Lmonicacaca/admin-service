package com.mbr.admin.common.manager.impl;

import com.mbr.admin.common.manager.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 *
 */
public class BaseManagerImpl<T> implements BaseManager<T> {
    @Autowired
    private BaseMapper<T> mapper;


    @Override
    public List<T> queryAll(T t) {
        return mapper.select(t);
    }

    @Override
    public T queryOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public T queryById(Object t) {
        return mapper.selectByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) throws Exception{
       return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public int updateByPrimaryKey(T t) throws Exception {
        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public int delete(T t) throws Exception{
        return mapper.delete(t);
    }

    @Override
    public int deleteById(Object t) throws Exception{
        return mapper.deleteByPrimaryKey(t);
    }

    @Override
    public int insert(T t) throws Exception {
        return mapper.insert(t);
    }


    @Override
    public int insertSelective(T t) throws Exception {
       return mapper.insertSelective(t);
    }

}
