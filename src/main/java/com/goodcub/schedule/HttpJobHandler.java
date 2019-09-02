package com.goodcub.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodcub.core.utils.HttpClientUtil;
import com.goodcub.shishicai.entity.SscTempInfo;
import com.goodcub.shishicai.service.FourStartService;
import com.goodcub.shishicai.service.impl.FourStartServiceImpl;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 跨平台Http任务
 *
 * @author xuxueli 2018-09-16 03:48:34
 */
@JobHandler(value = "httpJobHandler")
@Component
public class HttpJobHandler extends IJobHandler {

    private Logger logger = LoggerFactory.getLogger(HttpJobHandler.class);

    private static final String requestUrl = "http://77tj.org/api/tencent/onlineim";

    @Resource
    FourStartService fourStartService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        String tencentResult = HttpClientUtil.doGet(requestUrl);
        // logger.info("request result -->" + tencentResult);

        if(tencentResult!=null && !"".equals(tencentResult)){
            JSONArray jsonArray = (JSONArray) JSONArray.parse(tencentResult);
            if(jsonArray != null && jsonArray.size()> 0){

                JSONObject obj = jsonArray.getJSONObject(0);
                logger.info("最近一条记录 >>>>>>>> " + obj.toJSONString());

                SscTempInfo sscTempInfo =  fourStartService.queryCurentInfoById();

                return new ReturnT(200,obj.toJSONString());
            }else{
                return FAIL;
            }

        }else{
            return FAIL;
        }
    }

}
