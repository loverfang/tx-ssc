package com.goodcub.shishicai.service.impl;

import com.goodcub.shishicai.entity.SscTempInfo;
import com.goodcub.shishicai.mapper.SscTempInfoMapper;
import com.goodcub.shishicai.service.FourStartService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Luo.z.x
 * @Description: 四星出3码4码_补充定为
 * @Date 2019/9/2
 * @Version V1.0
 **/
@Service
public class FourStartServiceImpl implements FourStartService {
    protected Log log = LogFactory.getLog(this.getClass());

    @Resource
    private SscTempInfoMapper sscTempInfoMapper;

    @Override
    public SscTempInfo queryCurentInfoById() {
        return sscTempInfoMapper.queryCurentInfoById();
    }

}
