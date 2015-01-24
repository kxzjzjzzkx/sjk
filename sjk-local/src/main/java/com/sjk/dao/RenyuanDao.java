/**
 * @author kongsj
 * @date 2015年1月9日
 * 
 */
package com.sjk.dao;

import java.util.List;

import com.sjk.domain.PageDto;
import com.sjk.domain.Renyuan;

public interface RenyuanDao {
	public Integer insert(Renyuan renyuan);

	public List<Renyuan> list(Renyuan renyuan, PageDto<Renyuan> page);

	public Renyuan queryById(Integer id);

	public Integer update(Renyuan renyuan);

	public Integer delete(Integer id);
	
	public Integer count(Renyuan renyuan);
}
