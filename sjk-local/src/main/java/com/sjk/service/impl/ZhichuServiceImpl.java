/**
 * @author kongsj
 * @date 2015年1月13日
 * 
 */
package com.sjk.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sjk.dao.ZhichuDao;
import com.sjk.domain.PageDto;
import com.sjk.domain.Zhichu;
import com.sjk.service.ZhichuService;

@Component("zhichuService")
public class ZhichuServiceImpl implements ZhichuService {

	@Resource
	private ZhichuDao zhichuDao;
	
	@Override
	public Integer insert(Zhichu zhichu) {
		zhichu.setGmtCreated(new Date());
		zhichu.setGmtModified(new Date());
		return zhichuDao.insert(zhichu);
	}

	@Override
	public Zhichu queryById(Integer id) {
		return zhichuDao.queryById(id);
	}

	@Override
	public Integer update(Zhichu zhichu) {
		zhichu.setGmtModified(new Date());
		return zhichuDao.update(zhichu);
	}

	@Override
	public PageDto<Zhichu> page(Zhichu zhichu, PageDto<Zhichu> page) {
		do {
			List<Zhichu> list = zhichuDao.list(zhichu, page);
			page.setTotalRecords(list.size());
			List<Zhichu> nlist = new ArrayList<Zhichu>();
			int limit = page.getPageSize();
			int startIndex = page.getStartIndex();
			if (startIndex > page.getTotalRecords()) {
				page.setRecords(null);
				break;
			}
			for (int i = startIndex; 0 < limit; i++, limit--) {
				if (i >= list.size()) {
					break;
				}
				Zhichu obj = list.get(i);
				nlist.add(obj);
			}
			page.setRecords(nlist);
		} while (false);
		return page;
	}

	@Override
	public Integer delete(Integer id) {
		if (id==null) {
			return 0;
		}
		return zhichuDao.delete(id);
	}

}
