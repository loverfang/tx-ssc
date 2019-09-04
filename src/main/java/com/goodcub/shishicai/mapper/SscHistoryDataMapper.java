package com.goodcub.shishicai.mapper;

import com.goodcub.shishicai.entity.SscHistoryData;

import java.util.List;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2019/9/2
 * @Version V1.0
 **/
public interface SscHistoryDataMapper {

    /**
     * 插入基础数据
     * @param sscHistoryData
     * @return
     */
    int insertHistoryData(SscHistoryData sscHistoryData);

    /**
     * 获得最近的20条数据
     * @return
     */
    List<SscHistoryData> queryNearHistory();

    
}
