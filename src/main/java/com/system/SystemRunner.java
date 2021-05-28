package com.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Luffy
 * @description 系统启动器
 * @createTime 2021年05月08日 09:21:00
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class SystemRunner {
    public static void main(String[] args) {
        SpringApplication.run(SystemRunner.class,args);
    }
}
