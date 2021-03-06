package com.goodcub.api.controller;

import com.goodcub.api.service.ApiHeimaService;
import com.goodcub.core.utils.ResultResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2020/1/6
 * @Version V1.0
 **/
@RestController
@RequestMapping("api")
public class ApiHeimaController {
    protected Log log = LogFactory.getLog(this.getClass());

    @Resource
    private ApiHeimaService apiHeimaService;

    @RequestMapping("/result")
    @ResponseBody
    public ResultResponse result(int page, int limit, String order){
        return ResultResponse.ok().put("data", apiHeimaService.resultList(page, limit, order == null ? "asc":order));
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ResultResponse detail(String sscNumber){
        return ResultResponse.ok().put("data", apiHeimaService.resultDetail(sscNumber));
    }

}
