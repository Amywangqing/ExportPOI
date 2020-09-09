package com.qingfeng;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.qingfeng.mapper")
public class WxportPOIAPP {

    public static void main(String[] args) {
        SpringApplication.run(WxportPOIAPP.class,args);
    }
}
