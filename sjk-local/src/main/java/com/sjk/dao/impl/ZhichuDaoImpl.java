/**
 * @author kongsj
 * @date 2015年1月9日
 * 
 */
package com.sjk.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.sjk.dao.ZhichuDao;
import com.sjk.domain.PageDto;
import com.sjk.domain.Zhichu;

@Component("zhichuDao")
public class ZhichuDaoImpl extends SqlMapClientDaoSupport implements ZhichuDao {

	final static String SQL_PREFIX = "zhichu.";

	@Override
	public Integer insert(Zhichu zhichu) {
		return (Integer) getSqlMapClientTemplate().insert(SQL_PREFIX+"insert", zhichu);
	}

	@Override
	public Zhichu queryById(Integer id) {
		return (Zhichu) getSqlMapClientTemplate().queryForObject(SQL_PREFIX+"queryById", id);
	}

	@Override
	public Integer update(Zhichu zhichu) {
		return getSqlMapClientTemplate().update(SQL_PREFIX+"update", zhichu);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Zhichu> list(Zhichu zhichu, PageDto<Zhichu> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zhichu", zhichu);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(SQL_PREFIX+"list", map);
	}
	
	@Override
	public Integer delete(Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("gmtModified", new Date());
		return getSqlMapClientTemplate().delete(SQL_PREFIX+"delete", map);
	}

}
