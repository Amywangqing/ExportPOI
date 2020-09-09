package com.qingfeng.my.mapper;

import org.apache.poi.ss.formula.functions.T;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用的Mapper接口
 * 注意:公用通用接口MyMapper<T>要单独的存在另一个包和普通dao分开，以免启动类的@MapperScan扫描到会报错
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
