package com.goodcub;

import com.goodcub.core.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName TencentSSCApplication
 * @Description TODO
 * @Author Luo.z.x
 * @Date 2019/9/20:15
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan({"com.goodcub.shishicai.mapper"})
public class TencentSSCApplication {
    public static void main(String[] args) {
        SpringApplication.run(TencentSSCApplication.class, args);
    }
}
