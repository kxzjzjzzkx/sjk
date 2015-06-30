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
import org.springframework.web.servlet.ModelAndView;

import com.sjk.domain.Renyuan;
import com.sjk.service.RenyuanService;
import com.sjk.util.DateUtil;
import com.sjk.util.StringUtils;

/**
 * 
 */
@Controller
public class UserController {

	@Resource
	private RenyuanService renyuanService;
	
	/**
	 * 新人入职
	 * @param out
	 */
	@RequestMapping
	public void add(Map<String, Object>out,String result){
		out.put("result", result);
		out.put("dateStr", DateUtil.toString(new Date(), "yyyy-MM-dd"));
	}
	
	/**
	 * 新增人员
	 * @param out
	 * @param renyuan
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView doAdd(Map<String, Object> out,Renyuan renyuan,String gmtInStr) throws ParseException, UnsupportedEncodingException {
		if (!StringUtils.isContainCNChar(renyuan.getUsername())) {
			renyuan.setUsername(StringUtils.decryptUrlParameter(renyuan.getUsername()));
		}
		if (StringUtils.isNotEmpty(gmtInStr)) {
			renyuan.setGmtIn(DateUtil.getDate(gmtInStr, "yyyy-MM-dd"));
		}
		Integer i = renyuanService.create(renyuan);
		if (i>0) {
//			System.out.println("------新增员工成功------");
		}
		return new ModelAndView("redirect:/user/add.do?result="+i);
	}
	
	/**
	 * 编辑人员信息
	 * @param out
	 */
	@RequestMapping
	public void edit(Map<String, Object>out,Integer id){
		Renyuan renyuan = renyuanService.queryById(id);
		if (renyuan!=null) {
			out.put("renyuan", renyuan);
		}
	}
	
	/**
	 * 新增人员
	 * @param out
	 * @param renyuan
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView doEdit(Map<String, Object> out,Renyuan renyuan,String gmtInStr) throws ParseException, UnsupportedEncodingException {
		if (!StringUtils.isContainCNChar(renyuan.getUsername())) {
			renyuan.setUsername(StringUtils.decryptUrlParameter(renyuan.getUsername()));
		}
		if (StringUtils.isNotEmpty(gmtInStr)) {
			renyuan.setGmtIn(DateUtil.getDate(gmtInStr, "yyyy-MM-dd"));
		}
		Integer i = renyuanService.update(renyuan);
		if (i>0) {
//			System.out.println("------修改员工信息成功------");
		}
		return new ModelAndView("redirect:/renyuan.do");
	}
	
	/**
	 * 删除
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView doDelete(Map<String, Object>out,Integer id){
		Integer i = renyuanService.delete(id);
		if (i>0) {
			System.out.println("------删除员工信息成功------");
		}
		return new ModelAndView("redirect:/renyuan.do");
	}

}
