package com.goodcub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName TencentSSCApplication
 * @Description TODO
 * @Author Luo.z.x
 * @Date 2019/9/20:15
 * @Version 1.0
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan({"com.goodcub.shishicai.mapper"})
@EnableScheduling
@EnableRetry
public class TencentSSCApplication {
    public static void main(String[] args) {
        SpringApplication.run(TencentSSCApplication.class, args);
    }
}
