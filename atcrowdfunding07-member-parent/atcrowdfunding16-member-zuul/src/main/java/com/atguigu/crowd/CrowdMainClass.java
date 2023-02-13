package com.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author YUChangcan
 * @date 2023/1/16 - 16:18
 */
@EnableZuulProxy
@SpringBootApplication
public class CrowdMainClass {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass.class,args);
    }
}
