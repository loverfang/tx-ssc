package com.goodcub.shishicai.service.impl;

import com.goodcub.core.utils.IdWorker;
import com.goodcub.shishicai.entity.SscHistoryData;
import com.goodcub.shishicai.entity.SscTempInfo;
import com.goodcub.shishicai.mapper.SscHistoryDataMapper;
import com.goodcub.shishicai.mapper.SscTempInfoMapper;
import com.goodcub.shishicai.service.FourStartService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author Luo.z.x
 * @Description: 四星出3码4码_补充定位
 * @Date 2019/9/2
 * @Version V1.0
 **/
@Service
public class FourStartServiceImpl implements FourStartService {
    protected Log log = LogFactory.getLog(this.getClass());

    @Resource
    private IdWorker idWorker;

    @Resource
    private SscTempInfoMapper sscTempInfoMapper;

    @Resource
    private SscHistoryDataMapper sscHistoryDataMapper;

    @Override
    public SscTempInfo queryCurentInfoById() {
        return sscTempInfoMapper.queryCurentInfoById();
    }

    @Override
    public int updateOpenResult(SscTempInfo sscTempInfo, String result) {

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

        // 插入：四星出3码4码_补充定位结果记录
        return updateCount;
    }
}
