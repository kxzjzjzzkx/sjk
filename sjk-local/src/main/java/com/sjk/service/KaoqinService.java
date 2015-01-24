/**
 * @author kongsj
 * @date 2015年1月15日
 * 
 */
package com.sjk.service;

import java.util.List;

import com.sjk.domain.Kaoqin;

public interface KaoqinService {
	public Integer insert(Kaoqin kaoqin);

	public Integer update(Integer id,float timeWork);

	public Kaoqin queryById(Integer id);

	public List<Kaoqin> list(Kaoqin kaoqin);
}
