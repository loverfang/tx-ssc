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

    SscTempInfo queryCurentInfoById();

    int updateTempResult(SscTempInfo sscTempInfo);

}
