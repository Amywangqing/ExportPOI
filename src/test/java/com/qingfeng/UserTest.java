package com.qingfeng;

import com.qingfeng.mapper.UserMapper;
import com.qingfeng.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUser(){
        List<Map<String, Object>> maps = userMapper.exportUser();
        for (Map map : maps){
            System.out.println("map:"+map);
        }
    }


}
