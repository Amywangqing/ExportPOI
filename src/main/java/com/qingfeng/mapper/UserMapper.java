package com.qingfeng.mapper;

import com.qingfeng.my.mapper.MyMapper;
import com.qingfeng.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper extends MyMapper<User> {

    @Select("SELECT id,username,password,phone,create_time FROM user")
    public List<Map<String,Object>> exportUser();

    @Insert("INSERT INTO `user` (id,username,password,phone,create_time) VALUES(#{id},#{username},#{password},#{phone},#{createTime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")   //keyProperty java对象的属性；keyColumn表示数据库的字段
    int save(User user);


}
