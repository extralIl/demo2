package com.extra.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication()
//@MapperScan(basePackages = "com.extra.demo.mapper")//扫描mapper,如果在mapper上配置了注解就不用写这行了
@ServletComponentScan(basePackages = "com.extra.demo.filter")//扫描过滤器
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
