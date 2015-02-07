/**
 * @author kongsj
 * @date 2015年1月9日
 * 
 */
package com.sjk.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sjk.dao.RenyuanDao;
import com.sjk.domain.PageDto;
import com.sjk.domain.Renyuan;
import com.sjk.service.RenyuanService;
import com.sjk.util.StringUtils;

@Component("renyuanService")
public class RenyuanServiceImpl implements RenyuanService{

	@Resource
	private RenyuanDao renyuanDao;
	
	@Override
	public Integer create(Renyuan renyuan) {
		renyuan.setGmtCreated(new Date());
		renyuan.setGmtModified(new Date());
		if (renyuan.getUsermoney()<=0) {
			renyuan.setUsermoney(0);
		}
		if (!StringUtils.isNumber(renyuan.getUsermoneyMonth())) {
			renyuan.setUsermoneyMonth("0");
		}
		
		return renyuanDao.insert(renyuan);
	}

	@Override
	public PageDto<Renyuan> page(Renyuan renyuan, PageDto<Renyuan> page) throws UnsupportedEncodingException {
		do {
			if (StringUtils.isNotEmpty(renyuan.getUsername())&&!StringUtils.isContainCNChar(renyuan.getUsername())) {
				renyuan.setUsername(StringUtils.decryptUrlParameter(renyuan.getUsername()));
			}
			page.setTotalRecords(renyuanDao.count(renyuan));
			List<Renyuan> list = renyuanDao.list(renyuan, page);
			List<Renyuan> nlist = new ArrayList<Renyuan>();
			int limit = page.getPageSize();
			int startIndex = page.getStartIndex();
			if (startIndex>page.getTotalRecords()) {
				page.setRecords(null);
				break;
			}
			for (int i=startIndex; 0 < limit; i++,limit--) {
				if (i>=list.size()) {
					break;
				}
				Renyuan obj = list.get(i);
				nlist.add(obj);
			}
			page.setRecords(nlist);
		} while (false);
		return page;
	}

	@Override
	public Renyuan queryById(Integer id) {
		return renyuanDao.queryById(id);
	}

	@Override
	public Integer update(Renyuan renyuan) {
		renyuan.setGmtModified(new Date());
		return renyuanDao.update(renyuan);
	}

	@Override
	public Integer delete(Integer id) {
		return renyuanDao.delete(id);
	}
	
}
