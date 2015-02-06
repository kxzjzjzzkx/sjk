/**
 * @author kongsj
 * @date 2015年1月15日
 * 
 */
package com.sjk.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sjk.domain.Kaoqin;
import com.sjk.domain.PageDto;
import com.sjk.domain.Renyuan;
import com.sjk.service.KaoqinService;
import com.sjk.service.RenyuanService;
import com.sjk.util.DateUtil;
import com.sjk.util.StringUtils;

@Controller
public class KaoqinController {
	
	@Resource
	private KaoqinService kaoqinService;
	@Resource
	private RenyuanService renyuanService;
	
	/**
	 * 录入考勤页面 可多人
	 * @param out
	 * @param rid
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public void index(Map<String, Object>out,String rIds,Kaoqin kaoqin,String year,String month,String allFlag,String addRid) throws ParseException, UnsupportedEncodingException{
		if (StringUtils.isNotEmpty(year)&&StringUtils.isNotEmpty(month)) {
			Date from = DateUtil.getDate(year+"-"+month+"-"+"1", "yyyy-M-d");
			Date to = DateUtil.getDateAfterMonths(from, 1);
			kaoqin.setFrom(DateUtil.toString(from, "yyyy-M-d"));
			kaoqin.setTo(DateUtil.toString(to, "yyyy-M-d"));
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
		PageDto<Renyuan> page = renyuanService.page(new Renyuan(), new PageDto<Renyuan>());
		if (StringUtils.isNumber(addRid)) {
			String [] idArr = rIds.split(",");
			Set<String> idSet = new HashSet<String>();
			for (String id:idArr) {
				idSet.add(id);
			}
			idSet.add(addRid);
			rIds = "";
			for (String id: idSet) {
				rIds = rIds+ ","+ id;
			}
		}
		// 获取所选人员信息
		String[] ridArray = null;
		List<Renyuan> userList = new ArrayList<Renyuan>();
		if ("1".equals(allFlag)) {
			userList = page.getRecords();
			for (Renyuan obj : userList) {
				rIds = rIds +","+ obj.getId();
			}
			ridArray = rIds.split(",");
		}else{
			ridArray = rIds.split(",");
			for (String rid:ridArray) {
				if (!StringUtils.isNumber(rid)) {
					continue;
				}
				Renyuan obj = renyuanService.queryById(Integer.valueOf(rid));
				if (obj!=null) {
					userList.add(obj);
				}
			}
		}
		out.put("userList", userList);
		
		// 获取全员名单
		out.put("allUser", page.getRecords());
		
		
		// 竖型展示 考勤
		List<Kaoqin> list = new ArrayList<Kaoqin>(); // 定义总list
		// 获取所有人员的考勤
		for (String rid : ridArray) {
			if (!StringUtils.isNumber(rid)) {
				continue;
			}
			kaoqin.setRid(Integer.valueOf(rid));
			List<Kaoqin> resultList = kaoqinService.list(kaoqin);
			list.addAll(resultList);
		}
		// 分人分日期
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Kaoqin obj : list) {
			resultMap.put(""+DateUtil.toString(obj.getGmtWork(), "yyyy-M-d")+"-"+obj.getRid(), ""+obj.getTimeWork());
		}
		out.put("resultMap", resultMap);
//		else{
//			if (StringUtils.isNumber(rIds)) {
//				kaoqin.setRid(Integer.valueOf(rIds));
//				userStr = userStr + renyuanService.queryById(Integer.valueOf(rIds)).getUsername();
//				List<Kaoqin> list = kaoqinService.list(kaoqin);
//				Map<String, String> map = new HashMap<String, String>();
//				for (Kaoqin obj:list) {
//					map.put(DateUtil.toString(obj.getGmtWork(), "yyyy-M-d"), String.valueOf(obj.getTimeWork()));
//				}
//				out.put("map", map);
//				out.put("list", list);
//			}else if (StringUtils.isNotEmpty(rIds)) {
//				String [] ids = rIds.split(",");
//				for (String id : ids) {
//					Renyuan obj = renyuanService.queryById(Integer.valueOf(id));
//					if (obj==null) {
//						continue;
//					}
//					userStr = userStr + obj.getUsername()+" | ";
//				}
//			}
//		}
		
//		out.put("userStr", userStr);
		out.put("rid", rIds);
	}
	
	@RequestMapping
	public ModelAndView doAdd(Map<String, Object>out,String timeWork,String dayAndRid) throws IOException, ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		do{
			// 工时必须填
			if (StringUtils.isEmpty(timeWork)) {
				break;
			}
			// 用户id必须存在
			if (StringUtils.isEmpty(dayAndRid)) {
				break;
			}
			// 参数解析 2015-1-1-1。 '2015-1-1' 为日期  '-1' 为员工编号
			String[] param = dayAndRid.split("-");
			if (param.length!=4) {
				break;
			}
			Integer rid = Integer.valueOf(param[3]);
			Kaoqin kaoqin = new Kaoqin();
			kaoqin.setRid(rid);
			Renyuan obj = renyuanService.queryById(rid);
			if (obj==null) {
				continue;
			}
			kaoqin.setRname(obj.getUsername());
			kaoqin.setGmtWork(DateUtil.getDate(param[0]+"-"+param[1]+"-"+param[2], "yyyy-MM-dd"));
			Double tw = Double.valueOf(timeWork);
			kaoqin.setTimeWork(tw.floatValue());
			kaoqin.setGmtCreated(new Date());
			kaoqin.setGmtModified(new Date());
			kaoqinService.insert(kaoqin);
			map.put("success", true);
			
		}while(false);
		
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView doEdit(Map<String, Object>out,Integer id,float timeWork) throws IOException, ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		// 用户id必须存在
		if (id!=null) {
			kaoqinService.update(id, timeWork);
			map.put("success", true);
		}
		return printJson(map, out);
	}
	
	

	public ModelAndView printJson(Object obj, Map<String, Object> out)
			throws IOException {
		String jsonString = "";
		if (obj instanceof List) {
			JSONArray json = JSONArray.fromObject(obj);
			jsonString = (json.toString());
		} else {
			JSONObject json = JSONObject.fromObject(obj);
			jsonString = (json.toString());
		}
		out.put("json", jsonString);
		return new ModelAndView("json");
	}
}
