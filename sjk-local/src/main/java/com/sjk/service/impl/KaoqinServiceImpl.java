/**
 * @author kongsj
 * @date 2015年1月15日
 * 
 */
package com.sjk.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sjk.dao.KaoqinDao;
import com.sjk.domain.Kaoqin;
import com.sjk.service.KaoqinService;
import com.sjk.util.DateUtil;
import com.sjk.util.StringUtils;

@Component("kaoqinService")
public class KaoqinServiceImpl implements KaoqinService{
	
	@Resource
	private KaoqinDao kaoqinDao;

	@Override
	public Integer insert(Kaoqin kaoqin) {
		Kaoqin obj = kaoqinDao.queryIsExists(kaoqin.getRid(), DateUtil.toString(kaoqin.getGmtWork(), "yyyy-M-d"));
		Integer i = 0;
		if (obj!=null) {
			kaoqin.setId(obj.getId());
			i = update(obj.getId(),kaoqin.getTimeWork());
		}else{
			kaoqin.setGmtWorkStr(DateUtil.toString(kaoqin.getGmtWork(), "yyyy-M-d"));
			i = kaoqinDao.insert(kaoqin);
		}
		return i;
	}

	@Override
	public Integer update(Integer id, float timeWork) {
		Kaoqin kaoqin = new Kaoqin();
		kaoqin.setId(id);
		kaoqin.setTimeWork(timeWork);
		kaoqin.setGmtModified(new Date());
		return kaoqinDao.update(kaoqin);
	}

	@Override
	public Kaoqin queryById(Integer id) {
		return kaoqinDao.queryById(id);
	}

	@Override
	public List<Kaoqin> list(Kaoqin kaoqin) {
		try {
			if (StringUtils.isEmpty(kaoqin.getFrom())) {
				kaoqin.setFrom(DateUtil.toString(DateUtil.getDate(new Date(), "yyyy-MM-01"), "yyyy-MM-dd"));
			}
			if (StringUtils.isEmpty(kaoqin.getTo())) {
				kaoqin.setTo(DateUtil.toString(DateUtil.getDateAfterMonths(DateUtil.getDate(new Date(), "yyyy-MM-01"), 1), "yyyy-MM-dd"));
			}
		} catch (ParseException e) {
			
		}
		return kaoqinDao.list(kaoqin);
	}
	
}
