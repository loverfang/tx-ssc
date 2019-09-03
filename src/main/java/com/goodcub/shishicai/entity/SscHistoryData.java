package com.goodcub.shishicai.entity;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * @Author Luo.z.x
 * @Description: 历史数据
 * @Date 2019/9/2
 * @Version V1.0
 **/
@Data
public class SscHistoryData {
    private Long id;
    private String sscNumber;
    private String wan;
    private String qian;
    private String bai;
    private String shi;
    private String ge;
    private String result;
    private String sscTime;
}
