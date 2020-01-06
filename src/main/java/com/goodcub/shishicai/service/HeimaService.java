package com.goodcub.shishicai.service;

import com.goodcub.shishicai.entity.SscTempInfo;

/**
 * @Author Luo.z.x
 * @Description: 四星出3码4码_补充定位
 * @Date 2019/9/2
 * @Version V1.0
 **/
public interface HeimaService {

    /**
     * 系统中当前期数相关信息
     * @return
     */
    SscTempInfo queryCurentInfoById();

    /**
     * 更新开奖结果(黑马思路)
     * @param sscTempInfo
     * @return
     */
    void updateOpenResult(SscTempInfo sscTempInfo, String result,String currentDan,String preNumber);

}
