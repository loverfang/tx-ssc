package com.goodcub.core.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.chang
 * @项目：cloud-parent
 * @描述：
 * @创建时间：2017/12/28
 * @Copyright @2017 by Mr.chang
 */

@Configuration
public class XxlJobConfig {

    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    @Autowired
    private JobProperties jobProperties;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        logger.info("获取的参数：" + jobProperties.getIp() + "注册地址：" + jobProperties.getAddresses());
        XxlJobSpringExecutor xxlJobExecutor = new XxlJobSpringExecutor();
        xxlJobExecutor.setIp(jobProperties.getIp());
        xxlJobExecutor.setPort(jobProperties.getPort());
        xxlJobExecutor.setAppName(jobProperties.getAppname());
        xxlJobExecutor.setAdminAddresses(jobProperties.getAddresses());
        xxlJobExecutor.setLogPath(jobProperties.getLogpath());
        xxlJobExecutor.setAccessToken(jobProperties.getAccessToken());
        xxlJobExecutor.setLogRetentionDays(jobProperties.getLogretentiondays());
        return xxlJobExecutor;
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *         <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *         spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *         String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     *
     */
}
