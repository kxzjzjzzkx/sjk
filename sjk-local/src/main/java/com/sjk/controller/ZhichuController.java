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
import com.sjk.domain.Zhichu;
import com.sjk.service.RenyuanService;
import com.sjk.service.ZhichuService;
import com.sjk.util.DateUtil;
import com.sjk.util.StringUtils;

/**
 * 
 */
@Controller
public class ZhichuController {

	@Resource
	private ZhichuService zhichuService;
	@Resource
	private RenyuanService renyuanService;
	
	/**
	 * 新增支出
	 * @param out
	 */
	@RequestMapping
	public void add(Map<String, Object>out,Integer rid){
		Renyuan obj = renyuanService.queryById(rid);
		if (obj!=null) {
			out.put("username", obj.getUsername());
			out.put("rid", rid);
		}
		out.put("dateStr", DateUtil.toString(new Date(), "yyyy-MM-dd"));
	}
	
	/**
	 * 新增支出小计
	 * @param out
	 * @param renyuan
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView doAdd(Map<String, Object> out,Zhichu zhichu,String gmtGetStr) throws ParseException, UnsupportedEncodingException {
		if (StringUtils.isNotEmpty(zhichu.getRemark())&&!StringUtils.isContainCNChar(zhichu.getRemark())) {
			zhichu.setRemark(StringUtils.decryptUrlParameter(zhichu.getRemark()));
		}
		if (StringUtils.isNotEmpty(zhichu.getRname())&&!StringUtils.isContainCNChar(zhichu.getRname())) {
			zhichu.setRname(StringUtils.decryptUrlParameter(zhichu.getRname()));
		}
		if (StringUtils.isNotEmpty(gmtGetStr)) {
			zhichu.setGmtGet(DateUtil.getDate(gmtGetStr, "yyyy-MM-dd"));
		}
		Integer i = zhichuService.insert(zhichu);
		if (i>0) {
			return new ModelAndView("redirect:/zhichu.do?rid="+zhichu.getRid());
		}
		return new ModelAndView("redirect:/renyuan.do");
	}
	
	/**
	 * 编辑支出信息 不做
	 * @param out
	 */
	@RequestMapping
	public void edit(Map<String, Object>out,Zhichu zhichu,String gmtGetStr){
		
//		Renyuan renyuan = renyuanService.queryById(id);
//		if (renyuan!=null) {
//			out.put("renyuan", renyuan);
//		}
	}
	
	/**
	 * 修改支出人员 不做
	 * @param out
	 * @param renyuan
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public void doEdit(Map<String, Object> out,Renyuan renyuan,String gmtInStr) throws ParseException, UnsupportedEncodingException {
//		if (!StringUtils.isContainCNChar(renyuan.getUsername())) {
//			renyuan.setUsername(StringUtils.decryptUrlParameter(renyuan.getUsername()));
//		}
//		if (StringUtils.isNotEmpty(gmtInStr)) {
//			renyuan.setGmtIn(DateUtil.getDate(gmtInStr, "yyyy-MM-dd"));
//		}
//		Integer i = renyuanService.update(renyuan);
//		if (i>0) {
//			System.out.println("------修改员工信息成功------");
//		}
//		return new ModelAndView("redirect:/renyuan.do");
	}
	
	/**
	 * 删除
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView doDelete(Map<String, Object>out,Integer id){
		Integer i = zhichuService.delete(id);
		if (i>0) {
			System.out.println("------删除员工信息成功------");
		}
		return new ModelAndView("redirect:/zhichu.do");
	}

}
