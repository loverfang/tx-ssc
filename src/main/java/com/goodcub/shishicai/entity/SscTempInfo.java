package com.goodcub.shishicai.entity;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author Luo.z.x
 * @Description: 时时彩数据临时信息表
 * @Date 2019/9/2
 * @Version V1.0
 **/
@Data
public class SscTempInfo {
    private Long id;
    private String sscNumber;
    private String onlineNumber;
    private String onlineChange;
    private String onlineTime;
    private String currentDan;
    private String preNumber;
}
