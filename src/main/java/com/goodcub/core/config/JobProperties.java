package com.goodcub.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName JobProperties
 * @Description 任务调度配置资源文件
 * @Author Luo.z.x
 * @Date 2019/9/20:15
 * @Version 1.0
 */
@Data
@Configuration
@PropertySource("classpath:job.properties")
public class JobProperties {

    @Value("${xxl.job.admin.addresses}")
    private String addresses;

    @Value("${xxl.job.executor.appname}")
    private String appname;

    @Value("${xxl.job.executor.ip}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private Integer port;

    @Value("${xxl.job.executor.logpath}")
    private String logpath;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.logretentiondays}")
    private Integer logretentiondays;

}
