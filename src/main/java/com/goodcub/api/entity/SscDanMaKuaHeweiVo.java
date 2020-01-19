package com.goodcub.api.entity;

import lombok.Data;

/**
 * @ClassName SscDanMaKuaHewei
 * @Description TODO
 * @Author Luo.z.x
 * @Date 2020/1/517:32
 * @Version 1.0
 */
@Data
public class SscDanMaKuaHeweiVo {
    private Long id;
    private String sscNumber;   //'期数',
    private String sscDanmaAll;    //'当期使用的原始胆码(大/小胆码)',
    private String sscDanmaTou;    //'当期使用的胆码杀掉对码或者和尾后的胆码',
    private Integer sscHe;      //和值
    private Integer sscHewei;   //'开奖号码的和值/如果大于10就是和尾否则就是本身',
    private Integer sscHeweiDuima;    //'和尾的对数值',
    private Integer sscShadan;   //'杀掉的胆码/当和尾值的对码在胆码中则杀之，若不在胆码中则杀和和尾值',
    private Integer sscKuadu;   //'开奖号码的跨度',
    private Integer sscSwitch;  //'基础胆码切换状态',
    private String sscTouzhuma; //'投注的号码',
    private Integer sscTouzhumaCount; //'投注号码个数',
    private String sscShouyi;   //'收益(根据开奖结果算)',
    private Integer sscIsZhong; //'是否中奖',
    private String result;      //‘开奖号码’
}
