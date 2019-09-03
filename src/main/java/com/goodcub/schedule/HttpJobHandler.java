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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        String currentNumber = number();
        Date currentDate = new Date();

        SscTempInfo sscTempInfo =  fourStartService.queryCurentInfoById();

        //期数不相同
        if(!currentNumber.equals(sscTempInfo.getSscNumber())){

            //查询数据接口此次更行的数据信息
            String tencentResult = HttpClientUtil.doGet(requestUrl);
            if(tencentResult!=null && !"".equals(tencentResult)){

                JSONArray jsonArray = (JSONArray) JSONArray.parse(tencentResult);
                if(jsonArray != null && jsonArray.size()> 0){

                    JSONObject obj = jsonArray.getJSONObject(0);
                    logger.info("最近一条记录 >>>>>>>> " + obj.toJSONString());
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(currentDate);

                    if(obj!=null && !"".equals(obj.getString("onlinetime"))){
                        logger.info("第"+currentNumber+"期,结果："+results(obj.getString("onlinenumber")));
                        // 期数不同更新时间不是本分钟则修改数据
                        if(!currentTime.equals(obj.getString("onlinetime"))){
                            SscTempInfo tempInfo = new SscTempInfo();
                            tempInfo.setId(1L);
                            tempInfo.setSscNumber(currentNumber);
                            tempInfo.setOnlineNumber(obj.getString("onlinenumber"));
                            tempInfo.setOnlineChange(obj.getString("onlinechange"));
                            tempInfo.setOnlineTime(obj.getString("onlinetime"));
                            fourStartService.updateOpenResult(tempInfo, results(obj.getString("onlinenumber")));
                        }
                        return new ReturnT(200,obj.toJSONString());
                    }else{
                        logger.debug("数据加载失败!!!!");
                        // 官方数据开奖缓慢或者数据错误
                        // 用上期的数据填充(或者直接不管他数据情况),注意此处在本分钟结尾的时候修改
                        Integer currentSecond = Integer.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate).substring(currentTime.length()-2));
                        if(currentSecond>=58){
                            logger.debug("数据加载失败,默认数据填充！！！");
                            SscTempInfo tempInfo = new SscTempInfo();
                            tempInfo.setId(1L);
                            tempInfo.setOnlineTime(obj.getString("onlinetime"));
                            fourStartService.updateOpenResult(tempInfo, results(obj.getString("onlinenumber")));
                        }
                        return new ReturnT(200,obj.toJSONString());
                    }
                }else{
                    return FAIL;
                }
            }else{
                return FAIL;
            }
        }else{
            return FAIL;
        }
    }

    /**
     * 获得下一期的期号
     * @return
     */
    private String number(){
        //当前时间
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        long zero = calendar1.getTimeInMillis();
        long current=System.currentTimeMillis();  //当前时间毫秒数

        Long numberCountPart = (current - zero)/(1000*60);

        DecimalFormat df=new DecimalFormat("0000");
        String str2=df.format(numberCountPart);

        if("0000".equals(str2)){
            //等于0的时候期号为前一天的1440期
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
            String numberDatePart = new SimpleDateFormat("yyyyMMdd").format(calendar1.getTime());
            return numberDatePart + "-1440";
        }else{
            String numberDatePart = new SimpleDateFormat("yyyyMMdd").format(new Date());
            return numberDatePart + "-" + str2;

        }
    }

    private String results(String online){
        int sum = 0;
        StringBuilder finalResult = new StringBuilder();
        char [] s = online.toCharArray();
        boolean tag = false;
        for (int i = 0; i < s.length; i++) {
            sum += Integer.parseInt(String.valueOf(s[i]));
            if(i >= s.length - 4){
                if(tag){
                    finalResult.append(",");
                }
                finalResult.append(s[i]);
                tag = true;
            }
        }

        String sumStr = String.valueOf(sum);
        finalResult.insert(0, ",").insert(0, sumStr.substring(sumStr.length() - 1));
        return finalResult.toString();
    }

}
