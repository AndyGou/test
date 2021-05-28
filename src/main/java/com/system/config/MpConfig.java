package com.system.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Luffy
 * @description
 * @createTime 2021年05月25日 13:21:00
 */
@Configuration
@MapperScan("com.system.dao")
public class MpConfig {


    /**
    * <h1>分页插件<h1>
    */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
