package com.goodcub.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Luo.z.x
 * @Description: 通用的数据返回类
 * @Date 2019/9/19
 * @Version V1.0
 **/
public class ResultResponse extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	public ResultResponse() {
		put("code", 200);
		put("msg", "success");
	}
	
	public static ResultResponse error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static ResultResponse error(String msg) {
		return error(500, msg);
	}
	
	public static ResultResponse error(int code, String msg) {
		ResultResponse r = new ResultResponse();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static ResultResponse ok(String msg) {
		ResultResponse r = new ResultResponse();
		r.put("msg", msg);
		return r;
	}
	
	public static ResultResponse ok(Map<String, Object> map) {
		ResultResponse r = new ResultResponse();
		r.putAll(map);
		return r;
	}
	
	public static ResultResponse ok() {
		return new ResultResponse();
	}

	@Override
	public ResultResponse put(String key, Object value) {
		super.put(key, value);
		return this;
	}

}
