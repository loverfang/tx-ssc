package com.goodcub.shishicai.service.impl;

import com.goodcub.core.utils.IdWorker;
import com.goodcub.shishicai.entity.SscDingfiveFanfour;
import com.goodcub.shishicai.entity.SscHistoryData;
import com.goodcub.shishicai.entity.SscTempInfo;
import com.goodcub.shishicai.mapper.SscDingfiveFanfourMapper;
import com.goodcub.shishicai.mapper.SscHistoryDataMapper;
import com.goodcub.shishicai.mapper.SscTempInfoMapper;
import com.goodcub.shishicai.service.FourStartService;
import com.goodcub.shishicai.service.HeimaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Luo.z.x
 * @Description: 黑马思路
 * 遇到全大全小切换胆码，每一把杀掉前一把号码的对码或者和值，余下4码作为投注胆码生成号码，再从生成的号码中去除和尾值前一把号码跨度值的号码
 * @Date 2020/1/3
 * @Version V1.0
 **/
@Service
public class HeimaServiceImpl implements HeimaService {

    protected Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private IdWorker idWorker;

    @Resource
    private SscTempInfoMapper sscTempInfoMapper;

    @Resource
    private SscHistoryDataMapper sscHistoryDataMapper;

    @Resource
    private SscDingfiveFanfourMapper sscDingfiveFanfourMapper;

    @Override
    public SscTempInfo queryCurentInfoById() {
        return sscTempInfoMapper.queryCurentInfoById();
    }


    @Override
    public int updateOpenResult(SscTempInfo sscTempInfo, String result, String currentDan) {

        // 更新临时表
        int updateCount = sscTempInfoMapper.updateTempResult(sscTempInfo);

        // 插入数据结果表
        SscHistoryData currentData = new SscHistoryData();
        currentData.setId(idWorker.nextId());
        currentData.setSscNumber(sscTempInfo.getSscNumber());
        currentData.setWan(result.split(",")[0]);
        currentData.setQian(result.split(",")[1]);
        currentData.setBai(result.split(",")[2]);
        currentData.setShi(result.split(",")[3]);
        currentData.setGe(result.split(",")[4]);
        currentData.setResult(result);
        currentData.setSscTime(sscTempInfo.getOnlineTime());
        sscHistoryDataMapper.insertHistoryData(currentData);

        // 后三的结果
        String resultStr = result.split(",")[2] + "," + result.split(",")[3] + "," + result.split(",")[4];
        if(!StringUtils.isBlank(resultStr)) {

            String[] resultArr = resultStr.split(",");

            // step1 确定胆码
            // 是否换胆? 换大胆还是换小胆:0表示不换,1表示还成小胆,2表示换成大胆
            int switchTag = isSwitch(resultArr);

            String newDanStr = currentDan;

            // 等于0或者需要换的大小码与上一把胆码相同那么就不换胆
            if(switchTag == 1){
                // 换成小
                newDanStr = "0,1,2,3,4,";
            }else if(switchTag == 2){
                // 换成大
                newDanStr = "5,6,7,8,9,";
            }


            // 更新ssc_temp_info_attr表
            // TODO


            //step2 得到三个数的和值
            int sumValue = sumValue(resultArr);
            //step3 得到三个数的和尾值
            int heweiValue = sumValueLast(sumValue);
            //step4 根据和尾值得到它的对码值
            int duimaValue = sumDuishu(heweiValue);


            //step5 胆码中包含对码,就杀对码,没有对码就一定含有和尾值就杀和尾
            String newDan = "";
            if(newDanStr.contains(String.valueOf(duimaValue))){
                newDan = newDanStr.replace(String.valueOf(duimaValue)+",","");
            }else if (newDanStr.contains(String.valueOf(heweiValue))){
                newDan = newDanStr.replace(String.valueOf(heweiValue)+",","");
            }

            String danStr = newDan.substring(0, newDan.length() - 1);
            System.out.println("最后胆码:" + danStr);

            // step6 用胆码生成号码
            List<String> resultList = resultArr(newDan.substring(0, newDan.length() - 1));

            System.out.println("由胆码生成的号码:" + resultList.toString());
            System.out.println("号码个数:" + resultList.size());

            // step7 获取跨度
            int kuaduValue = kuadu(resultArr);
            System.out.println("跨度值:" + kuaduValue);


            List<String> finallResultList = new ArrayList<>();
            // step8 排除和值尾
            resultList.forEach(item -> {
                int len = item.length();

                int heVal = 0;
                for (int x=0;x<len; x++){
                    String c = String.valueOf(item.charAt(x));
                    heVal = heVal + Integer.valueOf(c);
                }

                if(heVal != kuaduValue && heVal%10 != kuaduValue){
                    finallResultList.add(item);
                }
            });

            finallResultList.forEach(item -> {
                System.out.println("投注码:" + item);
            });

            System.out.println("总共：" + finallResultList.size() + "注");
        }else{
            System.out.println("watting next ...");
        }

        // 更新结果表
        // TODO


        // sscDingfiveFanfourMapper.insertDingfiveFanfour(sscDingfiveFanfour);
        return updateCount;
    }


    public static int kuadu(String[] resultArr){
        java.util.Arrays.sort(resultArr);
        return Integer.valueOf(resultArr[resultArr.length-1]) - Integer.valueOf(resultArr[0]);
    }

    /**
     * 由胆码生成号码
     * @param danStr
     * @return
     */
    public static List<String> resultArr(String danStr){
        // 1000个基础数据
        List<String> lsit = Stream.iterate(0, item -> item+1 ).limit(1000)
                .map(item ->{
                    String str = item.toString();
                    while (str.length()<3) str = "0" + str;
                    return str;
                }).collect(Collectors.toList());

        List<String> result = new ArrayList<>();

        lsit.forEach(i -> {
            String[] danArr = danStr.split(",");
            for(int a=0; a<danArr.length; a++){
                if(i.contains(danArr[a])){
                    result.add(i);
                    break;
                }
            }
        });
        return result;
    }

    /**
     * 根据和尾值获得对数(就是要杀的码)
     * @param sumValueLast
     * @return
     */
    public static int sumDuishu(int sumValueLast){
        Map<String,Integer> duimaTable = new HashMap<>();
        duimaTable.put("dui_0",5);
        duimaTable.put("dui_1",6);
        duimaTable.put("dui_2",7);
        duimaTable.put("dui_3",8);
        duimaTable.put("dui_4",9);
        duimaTable.put("dui_5",0);
        duimaTable.put("dui_6",1);
        duimaTable.put("dui_7",2);
        duimaTable.put("dui_8",3);
        duimaTable.put("dui_9",4);

        // 根据和尾值取出对数
        return (duimaTable.get("dui_" + sumValueLast)).intValue();
    }

    /**
     * 三个数的和尾值
     * @return
     */
    public static int sumValueLast(int sumValue){
        // 获得和尾
        if(sumValue >= 10){
            return sumValue % 10;
        }else {
            return sumValue;
        }
    }

    /**
     * 三个数的和值
     * @param resultArr
     * @return
     */
    public static int sumValue(String[] resultArr){
        int value = 0;
        for(String result : resultArr){
            value = value + Integer.valueOf(result);
        }
        return value;
    }

    /**
     * 判断是否换号,且是换大号,还是换小号
     * 0不换号, 1换小号, 2换大号
     * @return
     */
    public static int isSwitch(String[] resultArr){
        // 判断号码是否是全大,全小,还是大小兼有
        int maxNum = 0;
        for(String result : resultArr){
            if(Integer.valueOf(result) >= 5){
                maxNum ++;
            }
        }
        if(maxNum == 3){
            return 2;
        }else if(maxNum == 0){
            return 1;
        }else {
            return 0;
        }
    }

}
