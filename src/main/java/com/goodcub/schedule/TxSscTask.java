package com.goodcub.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodcub.core.utils.HttpClientUtil;
import com.goodcub.shishicai.entity.SscTempInfo;
import com.goodcub.shishicai.service.HeimaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName TxSscTask
 * @Description TODO
 * @Author Luo.z.x
 * @Date 2020/1/1210:51
 * @Version 1.0
 */
@Component
public class TxSscTask {
    private Logger logger = LoggerFactory.getLogger(TxSscTask.class);
    private static final String requestUrl = "http://77tj.org/api/tencent/online";

    @Resource
    HeimaService heimaService;

    @Retryable(
        value={RetryException.class},  //出现指定异常就补偿
        maxAttempts=10,  //重试次数
        backoff = @Backoff(value = 1000)//每次重试延迟毫秒数
    )
    @Scheduled(cron = "3,15,30,50 * * * * ?")//没分钟的低3秒开始每20秒执行一次即：第3秒执行一次，第28秒执行一次,第53秒执行一次
    public void retry() {

        String currentNumber = number();
        Date currentDate = new Date();

        SscTempInfo sscTempInfo =  heimaService.queryCurentInfoById();

        //期数不相同
        if(!currentNumber.equals(sscTempInfo.getSscNumber())){

            //查询数据接口此次更行的数据信息
            String tencentResult = HttpClientUtil.doGet(requestUrl);
            if(tencentResult!=null && !"".equals(tencentResult)){

                JSONArray jsonArray = (JSONArray) JSONArray.parse(tencentResult);
                if(jsonArray != null && jsonArray.size()> 0){

                    JSONObject obj = jsonArray.getJSONObject(0);

                    String targetTimme = obj.getString("onlinetime").substring(0, obj.getString("onlinetime").lastIndexOf(":"));
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(currentDate);

                    if(obj != null && !"".equals(obj.getString("onlinetime"))){
                        logger.info("第"+currentNumber+"期,结果："+results(obj.getString("onlinenumber")));

                        // 期数不同更新时间不是本分钟则修改数据
                        if(currentTime.equals(targetTimme)){
                            SscTempInfo tempInfo = new SscTempInfo();
                            tempInfo.setId(1L);
                            tempInfo.setSscNumber(currentNumber);
                            tempInfo.setOnlineNumber(obj.getString("onlinenumber"));
                            tempInfo.setOnlineChange(obj.getString("onlinechange"));
                            tempInfo.setOnlineTime(obj.getString("onlinetime"));

                            SscTempInfo checkSscTempInfo =  heimaService.queryCurentInfoById();

                            // 防止超长任务产生的重复执行问题
                            if(!currentNumber.equals(checkSscTempInfo.getSscNumber())) {
                                heimaService.updateOpenResult(tempInfo, results(obj.getString("onlinenumber")),checkSscTempInfo.getCurrentDan(), checkSscTempInfo.getPreNumber());
                            }

                        }else{

                            logger.debug("数据加载失败!!!!");
                            // 官方数据开奖缓慢或者数据错误
                            // 用上期的数据填充(或者直接不管他数据情况),注意此处在本分钟结尾的时候修改
                            String currentFormatDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate);
                            String timeString = currentFormatDateString.substring(currentFormatDateString.length()-2);

                            Integer currentSecond = Integer.valueOf( timeString );

                            if(currentSecond >= 58){
                                logger.debug("数据加载失败,默认数据填充！！！");
                                SscTempInfo tempInfo = new SscTempInfo();
                                tempInfo.setId(1L);
                                tempInfo.setOnlineTime(obj.getString("onlinetime"));
                                heimaService.updateOpenResult(tempInfo, results(obj.getString("onlinenumber")), sscTempInfo.getCurrentDan(), sscTempInfo.getPreNumber());
                            }
                        }

                    }else{
                        throw new RetryException("开奖方返回的开奖结果是空数据,再次去拿数据.");
                    }

                }else{
                    throw new RetryException("数据处理异常了或返回数据格式错了,再次去拿数据.");
                }

            }else{
                throw new RetryException("时间到了,开奖方返回的是空数据,再次去拿数据.");
            }

        }else{
            throw new RetryException("时间到了,但是开奖方和我们数据相同,再次去拿数据.");
        }

    }

    @Recover
    public void recover(RetryException e) {
        // TODO
        // System.out.println( e.getMessage() );
    }

    /**
     * 获得当前分钟数应该对应的期号
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
