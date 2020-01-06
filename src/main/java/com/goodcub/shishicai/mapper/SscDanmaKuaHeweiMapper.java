package com.goodcub.shishicai.mapper;

import com.goodcub.shishicai.entity.SscDanMaKuaHewei;
import com.goodcub.shishicai.entity.SscTempInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2019/9/2
 * @Version V1.0
 **/
public interface SscDanmaKuaHeweiMapper {

    /**
     * 开奖后插入下一期需要下注的号码的信息
     * @param sscDanMaKuaHewei
     * @return
     */
    int insertDanmaKuaHewei(SscDanMaKuaHewei sscDanMaKuaHewei);


    /**
     * 开奖后更新中奖情况
     * @param sscDanMaKuaHewei
     * @return
     */
    int updateDanmaKuaHewei(SscDanMaKuaHewei sscDanMaKuaHewei);

    /**
     * 根据期数获得上一期的下注情况
     * @param sscNumber
     * @return
     */
    SscDanMaKuaHewei querySscDanMaKuaHeweiBySscNumber(String sscNumber);

    /**
     * 查询所有结果
     * @return
     */
    List<SscDanMaKuaHewei> querySscDanMaKuaHeweiList();

}
