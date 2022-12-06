package com.xws.bootpro;

import com.xws.bootpro.utils.sessionUtil.ManageSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.xws.bootpro.mapper")
@ServletComponentScan     //过滤器
public class BootproApplication {

    // 全局session变量
    public static ManageSession manageSession;

    public static void main(String[] args) {
        SpringApplication.run(BootproApplication.class, args);
    }

}
