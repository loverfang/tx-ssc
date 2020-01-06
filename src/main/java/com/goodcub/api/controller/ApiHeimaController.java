package com.goodcub.api.controller;

import com.goodcub.api.service.ApiHeimaService;
import com.goodcub.core.utils.ResultResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2020/1/6
 * @Version V1.0
 **/
@Controller
@RequestMapping("api")
public class ApiHeimaController {
    protected Log log = LogFactory.getLog(this.getClass());

    @Resource
    private ApiHeimaService apiHeimaService;

    @RequestMapping("/result")
    @ResponseBody
    public ResultResponse result(int page, int limit){
        return ResultResponse.ok().put("data", apiHeimaService.resultList(page, limit));
    }

}
