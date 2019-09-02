package com.goodcub.shishicai.entity;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2019/9/2
 * @Version V1.0
 **/
@Data
public class SscDingfiveFanfour {
    private Long id;
    private String sscNumber;
    private String sscShama;
    private String sscTouma;
    private Integer sscDingweiCount;  //'投注码定位胆中奖个数',
    private Double sscDingweiAmount;  //'定位胆中奖金额',
    private Integer sscFanmaResult;   //'杀码中3或4个的中奖情况:1中奖，0未中奖',
    private Double sccFanmaAmount;    //'反码中奖金额',
    private Double sccTotalAmount;    //'档期最后利润',
}
