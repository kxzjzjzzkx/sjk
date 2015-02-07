/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-15
 */
package com.sjk.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sjk.domain.PageDto;
import com.sjk.domain.Renyuan;
import com.sjk.domain.Zhichu;
import com.sjk.service.RenyuanService;
import com.sjk.service.ZhichuService;
import com.sjk.util.DateUtil;
import com.sjk.util.StringUtils;

/**
 * 
 */
@Controller
public class RootController {

	@Resource
	private RenyuanService renyuanService;
	@Resource
	private ZhichuService zhichuService;

	/**
	 * 目录页 首页
	 * @param out
	 */
	@RequestMapping
	public void index(Map<String, Object>out){
		
	}
	
	/**
	 * 人员页 
	 * @param out
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	@RequestMapping
	public void renyuan(Map<String, Object>out,PageDto<Renyuan> page,Renyuan renyuan,String year,String month) throws UnsupportedEncodingException, ParseException{
		page.setPageSize(18);
		
		// 选择时间
		if (StringUtils.isEmpty(year)||StringUtils.isEmpty(month)) {
			renyuan.setFrom(DateUtil.toString(new Date(), "yyyy-M-1"));
			renyuan.setTo(DateUtil.toString(DateUtil.getDateAfterMonths(new Date(), 1), "yyyy-M-1"));
		}else{
			renyuan.setFrom(DateUtil.toString(DateUtil.getDate(year+"-"+month+"-1", "yyyy-M-d"), "yyyy-M-1"));
			renyuan.setTo(DateUtil.toString(DateUtil.getDateAfterMonths(DateUtil.getDate(year+"-"+month+"-1", "yyyy-M-d"), 1), "yyyy-M-1"));
		}
		
		page = renyuanService.page(renyuan, page);
		out.put("page", page);
		if (StringUtils.isNotEmpty(renyuan.getUsername())) {
			out.put("username", renyuan.getUsername());
		}
		if (StringUtils.isNumber(year)) {
			out.put("yearStr", year);
		}else{
			out.put("yearStr", DateUtil.toString(new Date(), "yyyy"));
		}
		if (StringUtils.isNumber(month)) {
			out.put("monthStr", month);
		}else{
			out.put("monthStr", DateUtil.toString(new Date(), "M"));
		}
	}
	
	/**
	 * 支出页 
	 * @param out
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public void zhichu(Map<String, Object>out,PageDto<Zhichu> page,Zhichu zhichu) throws UnsupportedEncodingException{
		page.setPageSize(18);
		page = zhichuService.page(zhichu, page);
		if (zhichu.getRid()>0) {
			Renyuan obj = renyuanService.queryById(zhichu.getRid());
			out.put("renyuan", obj);
		}
		out.put("page", page);
	}

}
