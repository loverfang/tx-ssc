package com.goodcub.shishicai.service.impl;

import com.goodcub.core.utils.IdWorker;
import com.goodcub.shishicai.entity.SscDingfiveFanfour;
import com.goodcub.shishicai.entity.SscHistoryData;
import com.goodcub.shishicai.entity.SscTempInfo;
import com.goodcub.shishicai.mapper.SscDingfiveFanfourMapper;
import com.goodcub.shishicai.mapper.SscHistoryDataMapper;
import com.goodcub.shishicai.mapper.SscTempInfoMapper;
import com.goodcub.shishicai.service.FourStartService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author Luo.z.x
 * @Description: 四星出3码4码_补充定位
 * @Date 2019/9/2
 * @Version V1.0
 **/
@Service
public class FourStartServiceImpl implements FourStartService {
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
    public int updateOpenResult(SscTempInfo sscTempInfo, String result) {

        Set<Integer> numbersSet = new HashSet<Integer>(){{add(0);add(1);add(2);add(3);add(4);add(5);add(6);add(7);add(8);add(9);}};

        // 获取最近的20条数据
        List<SscHistoryData> historyDataList = sscHistoryDataMapper.queryNearHistory();

        // 计算上一期为止应该投注的号码从最近20期中按逆序排列出号码,然后按照百位,千位,十位,万位,个位放入投注号码中
        Set<Integer> shaMaSet = nearNumbers(historyDataList);
        if(shaMaSet == null ){
            logger.info("投注码计算失败!");
            return -1;
        }

        logger.info("本期投注被杀码(即后四中3码或四码):" + shaMaSet.toString());

        // 杀掉上一期的码，即为定位胆投注的码
        for (Integer shaMa : shaMaSet) {
            numbersSet.remove(shaMa);
        }

        logger.info("本期定位投注码(即五星定位胆所投码):" + numbersSet.toString());

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

        // 后四号码个数,验证是否有重号，有重号就算中,没有重号就算挂
        Set<String> housiNoSet = new HashSet<>();
        housiNoSet.add(currentData.getQian());
        housiNoSet.add(currentData.getBai());
        housiNoSet.add(currentData.getShi());
        housiNoSet.add(currentData.getGe());

        // 计算本期中奖的结果
        // 插入：四星出3码4码_补充定位结果记录
        SscDingfiveFanfour sscDingfiveFanfour = new SscDingfiveFanfour();
        sscDingfiveFanfour.setId(idWorker.nextId());
        sscDingfiveFanfour.setSscNumber(sscTempInfo.getSscNumber());

        String shamaStr = StringUtils.strip(shaMaSet.toString(),"[]");
        String toumaStr = StringUtils.strip(numbersSet.toString(),"[]");
        sscDingfiveFanfour.setSscShama(shamaStr);
        sscDingfiveFanfour.setSscTouma(toumaStr);


        String[] resultArr = result.split(",");
        String[] toumaArr = toumaStr.split(",");

        int dingweiCount = 0;
        for(String str : resultArr){
            for(String tou:toumaArr){
                if(tou.trim().equals(str.trim()) ){
                    dingweiCount = dingweiCount + 1;
                }
            }
        }
        logger.info("定位胆中奖个数:" + dingweiCount);
        sscDingfiveFanfour.setSscDingweiCount(dingweiCount);         //投注码定位胆中奖个数',
        sscDingfiveFanfour.setSscDingweiAmount(dingweiCount * 9.78); //'定位胆中奖金额',
        ////////////////////////////////投码结束//////////////////////////////////////////////////
        String[] shamaArr = shamaStr.split(",");
        int rongcuoCount = 0;
        for(int i=1;i<resultArr.length; i++){
            for(String sha:shamaArr){
                if(sha.trim().equals(resultArr[i].trim()) ){
                    rongcuoCount = rongcuoCount + 1;
                }
            }
        }

        logger.info("杀码后四个数:" + rongcuoCount);
        // 后四是否中奖
        Boolean housiResult =(rongcuoCount>=3 && housiNoSet.size()<4);
        sscDingfiveFanfour.setSscFanmaResult(housiResult?1:0); //'杀码中3或4个的中奖情况:1中奖，0未中奖',
        sscDingfiveFanfour.setSccFanmaAmount((housiResult?1:0)*19.56);   //'反码中奖金额', 3.36
         ////////////////////////////////杀码结束//////////////////////////////////////////////////

        Double lirun = dingweiCount * 9.78 + (housiResult?1:0)*19.56 - 3.61 - 25;
        logger.info("当期利润:" + lirun);
        sscDingfiveFanfour.setSccTotalAmount(lirun); //'档期最后利润': 定位胆中奖个数*9.78 + 反杀中奖金额 - 25定位胆成本- 反杀成本

        sscDingfiveFanfourMapper.insertDingfiveFanfour(sscDingfiveFanfour);
        return updateCount;
    }

    /**
     * 获得当前期应该下注的号码:即上一期的时候该投的号码,现在才来算,是因为我们只是统计,只需要知道结果
     * @param historyDataList
     * @return
     */
    private Set<Integer> nearNumbers(List<SscHistoryData> historyDataList){
        if(historyDataList!=null && historyDataList.size()>0){
            Set<Integer> touNumber = new HashSet<Integer>();

            for(int i=0;i<historyDataList.size();i++){
                SscHistoryData tempData = historyDataList.get(i);
                if(i==0){
                    touNumber.add(Integer.valueOf(tempData.getWan()));
                    touNumber.add(Integer.valueOf(tempData.getQian()));
                    touNumber.add(Integer.valueOf(tempData.getBai()));
                    touNumber.add(Integer.valueOf(tempData.getShi()));
                    touNumber.add(Integer.valueOf(tempData.getGe()));
                    if(touNumber.size()==5){
                        break;
                    }
                }else{
                    touNumber.add(Integer.valueOf(tempData.getBai()));
                    if(touNumber.size()==5){
                        break;
                    }
                    touNumber.add(Integer.valueOf(tempData.getQian()));
                    if(touNumber.size()==5){
                        break;
                    }

                    touNumber.add(Integer.valueOf(tempData.getShi()));
                    if(touNumber.size()==5){
                        break;
                    }

                    touNumber.add(Integer.valueOf(tempData.getWan()));
                    if(touNumber.size()==5){
                        break;
                    }

                    touNumber.add(Integer.valueOf(tempData.getGe()));
                    if(touNumber.size()==5){
                        break;
                    }
                }
            }
            return touNumber;
        }else{
            return null;
        }
    }
}
