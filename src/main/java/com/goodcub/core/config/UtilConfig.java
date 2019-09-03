package com.goodcub.core.config;

import com.goodcub.core.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName UtilConfig
 * @Description TODO
 * @Author Luo.z.x
 * @Date 2019/9/38:09
 * @Version 1.0
 */
@Configuration
public class UtilConfig {
    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }
}
