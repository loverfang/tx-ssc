package com.goodcub.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goodcub.api.service.ApiHeimaService;
import com.goodcub.core.utils.page.TableDataInfo;
import com.goodcub.shishicai.entity.SscDanMaKuaHewei;
import com.goodcub.shishicai.mapper.SscDanmaKuaHeweiMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2020/1/6
 * @Version V1.0
 **/
@Service
public class ApiHeimaServiceImpl implements ApiHeimaService {

    protected Log log = LogFactory.getLog(this.getClass());

    @Resource
    SscDanmaKuaHeweiMapper sscDanmaKuaHeweiMapper;

    @Override
    public TableDataInfo resultList(int page, int limit, String order) {

        PageHelper.startPage(page, limit).setOrderBy("ssc_number "+order);
        List<SscDanMaKuaHewei>  resultList = sscDanmaKuaHeweiMapper.querySscDanMaKuaHeweiList();
        PageInfo<SscDanMaKuaHewei> resultPageInfo = new PageInfo<>(resultList);

        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setItems(resultPageInfo.getList());
        tableDataInfo.setTotal(resultPageInfo.getTotal());
        tableDataInfo.setPageNum(tableDataInfo.getPageNum());
        tableDataInfo.setPageSize(tableDataInfo.getPageSize());
        tableDataInfo.setPages(resultPageInfo.getPages());

        return tableDataInfo;
    }

}
