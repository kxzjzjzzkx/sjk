/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-15
 */
package com.sjk.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public void renyuan(Map<String, Object>out,PageDto<Renyuan> page,Renyuan renyuan) throws UnsupportedEncodingException, ParseException{
		page.setPageSize(18);
		page = renyuanService.page(renyuan, page);
		out.put("page", page);
		if (StringUtils.isNotEmpty(renyuan.getUsername())) {
			out.put("username", renyuan.getUsername());
		}
		out.put("yearStr", DateUtil.toString(new Date(), "yyyy"));
		out.put("monthStr", DateUtil.toString(new Date(), "M"));
	}
	
	/**
	 * 管理界面 
	 * @param out
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	@RequestMapping
	public void manage(Map<String, Object>out,PageDto<Renyuan> page,Renyuan renyuan,String year,String month,String addRid,String fromYearStr,String toYearStr,String existId) throws UnsupportedEncodingException, ParseException{
		page.setPageSize(18);
		
		if (StringUtils.isNotEmpty(fromYearStr)) {
			out.put("fromYearStr", fromYearStr);
		}
		if (StringUtils.isNotEmpty(toYearStr)) {
			out.put("toYearStr", toYearStr);
		}
		
		// 选择时间
		if (StringUtils.isEmpty(year)||StringUtils.isEmpty(month)) {
			renyuan.setFrom(DateUtil.toString(new Date(), "yyyy-M-1"));
			renyuan.setTo(DateUtil.toString(DateUtil.getDateAfterMonths(new Date(), 1), "yyyy-M-1"));
		}else{
			renyuan.setFrom(DateUtil.toString(DateUtil.getDate(year+"-"+month+"-1", "yyyy-M-d"), "yyyy-M-1"));
			renyuan.setTo(DateUtil.toString(DateUtil.getDateAfterMonths(DateUtil.getDate(year+"-"+month+"-1", "yyyy-M-d"), 1), "yyyy-M-1"));
		}
		
		PageDto<Renyuan> userPage = renyuanService.page(new Renyuan(), new PageDto<Renyuan>());
		out.put("allUser", userPage.getRecords());
		
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
		
		// 获取添加的人员
		if (StringUtils.isNotEmpty(addRid)) {
			existId = existId+","+ addRid;
		}
		if (StringUtils.isNotEmpty(existId)) {
			String [] addStrArray = existId.split(",");
			Set<String> idSet = new HashSet<String>();
			for (String id:addStrArray) {
				idSet.add(id);
			}
			List<Renyuan> list = new ArrayList<Renyuan>();
			for (String id:idSet) {
				if (!StringUtils.isNumber(id)) {
					continue;
				}
				Renyuan obj = renyuanService.queryById(Integer.valueOf(id));
				if (obj==null) {
					continue;
				}
				existId = existId+","+id;
				list.add(obj);
			}
			out.put("existId", existId);
			out.put("addList", list);
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
