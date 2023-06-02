package com.allblue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com.allblue.modules.*.dao"})
@EnableTransactionManagement
@EnableWebSecurity   //开启security
public class FastModelApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastModelApplication.class, args);
    }

}
