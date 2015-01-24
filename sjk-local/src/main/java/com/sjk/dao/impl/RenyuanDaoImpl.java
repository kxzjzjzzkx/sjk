/**
 * @author kongsj
 * @date 2015年1月9日
 * 
 */
package com.sjk.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.sjk.dao.RenyuanDao;
import com.sjk.domain.PageDto;
import com.sjk.domain.Renyuan;

@Component("renyuanDao")
public class RenyuanDaoImpl extends SqlMapClientDaoSupport implements RenyuanDao {

	final static String SQL_PREFIX = "renyuan.";

	@Override
	public Integer insert(Renyuan renyuan) {
		return (Integer) getSqlMapClientTemplate().insert(SQL_PREFIX + "insert", renyuan);
	}

	@Override
	public Renyuan queryById(Integer id) {
		return (Renyuan) getSqlMapClientTemplate().queryForObject(SQL_PREFIX+"queryById", id);
	}

	@Override
	public Integer update(Renyuan renyuan) {
		return getSqlMapClientTemplate().update(SQL_PREFIX+"update", renyuan);
	}

	@Override
	public Integer delete(Integer id) {
		return getSqlMapClientTemplate().update(SQL_PREFIX+"delete", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Renyuan> list(Renyuan renyuan, PageDto<Renyuan> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("renyuan", renyuan);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(SQL_PREFIX+"list", map);
	}

	@Override
	public Integer count(Renyuan renyuan) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("renyuan", renyuan);
		return (Integer) getSqlMapClientTemplate().queryForObject(SQL_PREFIX+"count", map);
	}

}
