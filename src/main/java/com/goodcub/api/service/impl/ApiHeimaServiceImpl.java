package com.goodcub.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goodcub.api.entity.SscDanMaKuaHeweiVo;
import com.goodcub.api.service.ApiHeimaService;
import com.goodcub.core.utils.page.TableDataInfo;
import com.goodcub.shishicai.entity.SscDanMaKuaHewei;
import com.goodcub.shishicai.mapper.SscDanmaKuaHeweiMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2020/1/6
 * @Version V1.0
 **/
public class ApiHeimaServiceImpl implements ApiHeimaService {

    protected Log log = LogFactory.getLog(this.getClass());

    @Resource
    SscDanmaKuaHeweiMapper sscDanmaKuaHeweiMapper;

    @Override
    public TableDataInfo resultList(int page, int limit) {

        PageHelper.startPage(page, limit).setOrderBy("ssc_number desc");
        PageInfo<SscDanMaKuaHewei> resultPageInfo = new PageInfo<>(sscDanmaKuaHeweiMapper.querySscDanMaKuaHeweiList());

        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setItems(resultPageInfo.getList());
        tableDataInfo.setTotal(resultPageInfo.getTotal());
        tableDataInfo.setPageNum(tableDataInfo.getPageNum());
        tableDataInfo.setPageSize(tableDataInfo.getPageSize());
        tableDataInfo.setPages(resultPageInfo.getPages());

        return tableDataInfo;
    }

}
