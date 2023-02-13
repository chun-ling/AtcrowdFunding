package com.atguigu.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YUChangcan
 * @date 2023/1/13 - 21:27
 */
@MapperScan("com.atguigu.crowd.mapper")
@SpringBootApplication
public class CrowdMainClass {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass.class,args);
    }
}
