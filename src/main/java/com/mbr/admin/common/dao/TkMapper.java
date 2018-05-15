package com.mbr.admin.common.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 *
 */
public interface TkMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
