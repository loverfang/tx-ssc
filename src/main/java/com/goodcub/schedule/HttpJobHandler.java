package com.goodcub.schedule;

import com.goodcub.core.config.XxlJobConfig;
import com.goodcub.core.utils.HttpClientUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 跨平台Http任务
 *
 * @author xuxueli 2018-09-16 03:48:34
 */
@JobHandler(value = "httpJobHandler")
@Component
public class HttpJobHandler extends IJobHandler {

    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    private static final String requestUrl = "http://77tj.org/api/tencent/onlineim";

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        String tencentResult = HttpClientUtil.doGet(requestUrl);

        logger.info("request result -->" + tencentResult);

        return ReturnT.SUCCESS;
    }

}
