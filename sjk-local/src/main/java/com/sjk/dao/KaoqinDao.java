/**
 * @author kongsj
 * @date 2015年1月15日
 * 
 */
package com.sjk.dao;

import java.util.List;

import com.sjk.domain.Kaoqin;

public interface KaoqinDao {
	public Integer insert(Kaoqin kaoqin);

	public Integer update(Kaoqin kaoqin);

	public Kaoqin queryById(Integer id);

	public List<Kaoqin> list(Kaoqin kaoqin);

	public Kaoqin queryIsExists(Integer rid,String gmtWork);
}
