package com.goodcub.shishicai.mapper;

import com.goodcub.shishicai.entity.SscTempInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2019/9/2
 * @Version V1.0
 **/
public interface SscTempInfoMapper {

    /**
     * 获得临时表中当前期数信息
     * @return
     */
    SscTempInfo queryCurentInfoById();

    /**
     * 更新临时信息表
     * @param sscTempInfo
     * @return
     */
    int updateTempResult(SscTempInfo sscTempInfo);

}
