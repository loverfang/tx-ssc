package com.goodcub.api.controller;

import com.goodcub.api.entity.SscDanMaKuaHeweiVo;
import com.goodcub.core.utils.ResultResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/result")
    @ResponseBody
    public ResultResponse result(){
        List<SscDanMaKuaHeweiVo> resultList = new ArrayList<>();
        return ResultResponse.ok().put("data", resultList);
    }
}
