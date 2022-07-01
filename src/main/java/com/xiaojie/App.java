package com.xiaojie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.xiaojie.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
