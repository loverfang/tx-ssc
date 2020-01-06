package com.goodcub.api.service;

import com.github.pagehelper.PageInfo;
import com.goodcub.api.entity.SscDanMaKuaHeweiVo;
import com.goodcub.core.utils.page.TableDataInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2020/1/6
 * @Version V1.0
 **/
public interface ApiHeimaService {
    TableDataInfo resultList(int page, int limit);
}
