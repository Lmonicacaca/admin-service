package com.mbr.admin.common.controller;


import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.common.dto.ResultConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ethan
 * @date 2016年10月12日 下午4:48:38
 * @email windofdusk@gmail.com
 * 类说明
 */
public abstract class BaseController<T> {

	protected Integer DEFAULT_PAGE_NO = 1;

	protected Integer DEFAULT_PAGE_SIZE = 10;

	protected String CODE_SUCCESS = "200";

	protected String CODE_FAILED = "500";


	protected Map<String, Object> success(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", CODE_SUCCESS);
		result.put("message","操作成功");
		return result;
	}
	
	protected Map<String, Object> success(Object ret){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", CODE_SUCCESS);
		result.put("message","操作成功");
		result.put("data", ret);
		return result;
	}

	protected Map<String, Object> error(){
		Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", CODE_FAILED);
		result.put("message","操作失败!");
		return result;
	}
	
	protected Map<String, Object> failed(){
		return this.failed(ResultConstant.FAILED_MSG);
	}
	
	protected Map<String, Object> failed(String msg){
		Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", CODE_FAILED);
		result.put("message", msg);
		return result;
	}

	protected Integer getPageNo(HttpServletRequest request){
		try{
			return Integer.valueOf(request.getParameter("start")) / this.getPageSize(request) + 1;
		}catch (Exception e){
			return this.DEFAULT_PAGE_NO;
		}
	}

	protected Integer getPageSize(HttpServletRequest request){
		try{
			return Integer.valueOf(request.getParameter("length"));
		}catch (Exception e){
			return this.DEFAULT_PAGE_SIZE;
		}
	}

	protected PageResultDto result(List list, Long total){
		PageInfo pageInfo = new PageInfo();
		pageInfo.setTotal(total);
		pageInfo.setList(list == null ? new ArrayList() : list);
		return new PageResultDto(pageInfo);
	}
}
