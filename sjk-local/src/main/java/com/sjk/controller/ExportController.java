/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-15
 */
package com.sjk.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sjk.domain.Kaoqin;
import com.sjk.domain.PageDto;
import com.sjk.domain.Renyuan;
import com.sjk.domain.Zhichu;
import com.sjk.service.KaoqinService;
import com.sjk.service.RenyuanService;
import com.sjk.service.ZhichuService;
import com.sjk.util.DateUtil;
import com.sjk.util.StringUtils;

/**
 * 
 */
@Controller
public class ExportController {

	@Resource
	private ZhichuService zhichuService;
	@Resource
	private RenyuanService renyuanService;
	@Resource
	private KaoqinService kaoqinService;
	
	
	
	/**
	 * 导出工资
	 * @param out
	 * @param id
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object>out,String rIds,HttpServletResponse response,String year,String month,String shenghuofei,String allFlag) throws RowsExceededException, WriteException, IOException, ParseException{
		//生成Excel
		response.setContentType("application/msexcel");
		OutputStream os = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename="+DateUtil.toString(new Date(), "yyyy-MM-dd")+".xls");
		
		if (StringUtils.isEmpty(year)) {
			year = DateUtil.toString(new Date(), "yyyy");
		}
		if (StringUtils.isEmpty(month)) {
			month = DateUtil.toString(new Date(), "M");
			Integer intMonth = Integer.valueOf(month)-1;
			if (intMonth==0) {
				intMonth =12;
			}
			month = ""+intMonth;
		}
		if (StringUtils.isEmpty(shenghuofei)||!StringUtils.isNumber(shenghuofei)) {
			shenghuofei = "15";
		}
		
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		
		String[] ids = null;
		if ("1".equals(allFlag)) {
			rIds="0";
			PageDto<Renyuan> page = renyuanService.page(new Renyuan(), new PageDto<Renyuan>());
			for (Renyuan obj: page.getRecords()) {
				rIds = rIds+","+ obj.getId();
			}
		}
		if (StringUtils.isEmpty(rIds)) {
			return null;
		}
		
		if (rIds.indexOf(",")!=-1) {
			ids = rIds.split(",");
		}else{
			ids = new String[]{rIds};
		}
		
		
		ws.addCell(new Label(0,0,"姓名"));
		ws.addCell(new Label(1,0,"月份"));
		ws.addCell(new Label(2,0,"日工资(元)"));
		ws.addCell(new Label(3,0,"支出总额(元)"));
		ws.addCell(new Label(4,0,"生活费(元)"));
		ws.addCell(new Label(5,0,"实发工资(元)"));
		ws.addCell(new Label(6,0,"工时"));
		int i=1;
		for(String id:ids){
			
			Renyuan renyuan = renyuanService.queryById(Integer.valueOf(id));
			
			if (renyuan==null) {
				continue;
			}
			
			ws.addCell(new Label(0,i,renyuan.getUsername())); //姓名
			ws.addCell(new Label(1,i,year+"年"+month+"月")); //月份 
			ws.addCell(new Label(2,i,""+renyuan.getUsermoney())); // 日工资
			
			
			Zhichu zhichu = new Zhichu();
			Date fromDate = DateUtil.getDate(year+"-"+month+"-1", "yyyy-M-d");
			Date toDate = DateUtil.getDateAfterMonths(fromDate, 1);
			zhichu.setFrom(DateUtil.toString(fromDate, "yyyy-M-d"));
			zhichu.setTo(DateUtil.toString(toDate, "yyyy-M-d"));
			zhichu.setRid(Integer.valueOf(id));
			List<Zhichu> zhichuList = zhichuService.page(zhichu, new PageDto<Zhichu>()).getRecords();
			Float zhichuMN = 0f;
			for (Zhichu obj:zhichuList) {
				zhichuMN = zhichuMN + obj.getMoneyGet();
			}
			ws.addCell(new Label(3,i,""+zhichuMN)); // 提前支出
			Kaoqin kaoqin = new Kaoqin();
			kaoqin.setFrom(DateUtil.toString(fromDate, "yyyy-M-d"));
			kaoqin.setTo(DateUtil.toString(toDate, "yyyy-M-d"));
			kaoqin.setRid(Integer.valueOf(id));
			List<Kaoqin> kaoqinList = kaoqinService.list(kaoqin);
			//工时
			Float timeWork = 0f;
			for (Kaoqin obj:kaoqinList) {
				timeWork = timeWork + obj.getTimeWork();
			}
			ws.addCell(new Label(6,i,""+timeWork)); // 总工时
			Integer shenghuofeiMN = kaoqinList.size()*Integer.valueOf(shenghuofei);
			ws.addCell(new Label(4,i,""+shenghuofeiMN)); // 生活费 工作 天数*15 
			
			// 实发工资 = 日工资 / 9 * 工时 - 支出 - 生活费(工作天数*生活费额度)
			Float total = Float.valueOf(renyuan.getUsermoney());
			Float finalGet = total /9 ;
			finalGet = finalGet * timeWork;
			finalGet = finalGet - zhichuMN;
			finalGet = finalGet - shenghuofeiMN;
			
			ws.addCell(new Label(5,i,""+finalGet));
			i++;
		}
		
		wwb.write();
		wwb.close();
		os.close(); 
		return null;
	}
	
	@RequestMapping
	public void check(Map<String, Object> out,Integer rid,String month,String year,String shenghuofei) throws ParseException{
		out.put("rid", rid);
		if (StringUtils.isEmpty(year)) {
			year = DateUtil.toString(new Date(), "yyyy");
		}
		if (StringUtils.isEmpty(month)) {
			month = DateUtil.toString(new Date(), "M");
			Integer intMonth = Integer.valueOf(month)-1;
			if (intMonth==0) {
				intMonth =12;
			}
			month = ""+intMonth;
		}
		if (StringUtils.isNotEmpty(year)&&StringUtils.isNotEmpty(month)) {
			Date from = DateUtil.getDate(year+"-"+month+"-"+"1", "yyyy-M-d");
			Date to = DateUtil.getDateAfterMonths(from, 1);
			out.put("todayStr", DateUtil.toString(from, "yyyy-M"));
			out.put("yearStr", DateUtil.toString(from, "yyyy"));
			out.put("monthStr", DateUtil.toString(from, "M"));
			out.put("days", DateUtil.getIntervalDays(to,from));
		}else{
			out.put("todayStr", DateUtil.toString(new Date(), "yyyy-M"));
			out.put("yearStr", DateUtil.toString(new Date(), "yyyy"));
			out.put("monthStr", DateUtil.toString(new Date(), "M"));
			out.put("days", DateUtil.getIntervalDays(DateUtil.getDateAfterMonths(DateUtil.getDate(new Date(), "yyyy-MM-01"), 1),DateUtil.getDate(new Date(), "yyyy-MM-01")));
		}
		if (StringUtils.isEmpty(shenghuofei)||!StringUtils.isNumber(shenghuofei)) {
			shenghuofei = "15";
		}
		out.put("shenghuofei", shenghuofei);
		
		Renyuan renyuan = renyuanService.queryById(rid);
		out.put("name", renyuan.getUsername()); //姓名
		out.put("checkDate", year+"年"+month+"月");
		out.put("money", renyuan.getUsermoney()); // 日工资
		
		Zhichu zhichu = new Zhichu();
		Date fromDate = DateUtil.getDate(year+"-"+month+"-1", "yyyy-M-d");
		Date toDate = DateUtil.getDateAfterMonths(fromDate, 1);
		zhichu.setFrom(DateUtil.toString(fromDate, "yyyy-M-d"));
		zhichu.setTo(DateUtil.toString(toDate, "yyyy-M-d"));
		zhichu.setRid(rid);
		List<Zhichu> zhichuList = zhichuService.page(zhichu, new PageDto<Zhichu>()).getRecords();
		out.put("zhichuList", zhichuList);
		Float zhichuMN = 0f;
		for (Zhichu obj:zhichuList) {
			zhichuMN = zhichuMN + obj.getMoneyGet();
		}
		out.put("zhichuMN", zhichuMN); // 提前支出
		Kaoqin kaoqin = new Kaoqin();
		kaoqin.setFrom(DateUtil.toString(fromDate, "yyyy-M-d"));
		kaoqin.setTo(DateUtil.toString(toDate, "yyyy-M-d"));
		kaoqin.setRid(rid);
		List<Kaoqin> kaoqinList = kaoqinService.list(kaoqin);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Kaoqin obj:kaoqinList) {
			map.put(DateUtil.toString(obj.getGmtWork(), "yyyy-M-d"), String.valueOf(obj.getTimeWork()));
		}
		out.put("map", map);
		out.put("kaoqinList", kaoqinList);
		//工时
		Float timeWork = 0f;
		for (Kaoqin obj:kaoqinList) {
			timeWork = timeWork + obj.getTimeWork();
		}
		out.put("timeWork", timeWork); // 总工时
		Integer shenghuofeiMN = kaoqinList.size()*Integer.valueOf(shenghuofei);
		out.put("shenghuofeiMN", shenghuofeiMN); // 生活费 工作 天数*15 
		
		// 实发工资 = 日工资 / 9 * 工时 - 支出 - 生活费(工作天数*生活费额度)
		Float total = Float.valueOf(renyuan.getUsermoney());
		Float finalGet = total /9 ;
		finalGet = finalGet * timeWork;
		finalGet = finalGet - zhichuMN;
		finalGet = finalGet - shenghuofeiMN;
		out.put("finalGet", finalGet);
		
	}

}
