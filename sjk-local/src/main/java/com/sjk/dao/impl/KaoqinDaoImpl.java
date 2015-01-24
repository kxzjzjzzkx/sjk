/**
 * @author kongsj
 * @date 2015年1月15日
 * 
 */
package com.sjk.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.sjk.dao.KaoqinDao;
import com.sjk.domain.Kaoqin;

@Component("kaoqinDao")
public class KaoqinDaoImpl extends SqlMapClientDaoSupport  implements KaoqinDao{

	final static String SQL_PREFIX = "kaoqin.";
	
	@Override
	public Integer insert(Kaoqin kaoqin) {
		return (Integer) getSqlMapClientTemplate().insert(SQL_PREFIX+"insert", kaoqin);
	}

	@Override
	public Integer update(Kaoqin kaoqin) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", kaoqin.getId());
		map.put("gmtModified", kaoqin.getGmtModified());
		map.put("timeWork", kaoqin.getTimeWork());
		return getSqlMapClientTemplate().update(SQL_PREFIX+"update",map);
	}

	@Override
	public Kaoqin queryById(Integer id) {
		return (Kaoqin) getSqlMapClientTemplate().queryForObject(SQL_PREFIX+"queryById",id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kaoqin> list(Kaoqin kaoqin) {
		Map<String, Object > map = new HashMap<String, Object>();
		map.put("kaoqin", kaoqin);
		return getSqlMapClientTemplate().queryForList(SQL_PREFIX+"list", map);
	}
	
	@Override
	public Kaoqin queryIsExists(Integer rid,String gmtWork) {
		Map<String, Object > map = new HashMap<String, Object>();
		map.put("rid", rid);
		map.put("gmtWork", gmtWork);
		return (Kaoqin) getSqlMapClientTemplate().queryForObject(SQL_PREFIX+"queryIsExists",map);
	}

}
