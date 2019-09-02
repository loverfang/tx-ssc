package com.goodcub.shishicai.service;

import com.goodcub.shishicai.entity.SscTempInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author Luo.z.x
 * @Description: 四星出3码4码_补充定为
 * @Date 2019/9/2
 * @Version V1.0
 **/
public interface FourStartService {

    /**
     * 系统中当前期数相关信息
     * @return
     */
    SscTempInfo queryCurentInfoById();


}
