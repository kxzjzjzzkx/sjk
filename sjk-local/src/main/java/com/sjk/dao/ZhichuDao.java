/**
 * @author kongsj
 * @date 2015年1月13日
 * 
 */
package com.sjk.dao;

import java.util.List;

import com.sjk.domain.PageDto;
import com.sjk.domain.Zhichu;

public interface ZhichuDao {

	public Integer insert(Zhichu zhichu);

	public Zhichu queryById(Integer id);

	public Integer update(Zhichu zhichu);

	public List<Zhichu> list(Zhichu zhichu, PageDto<Zhichu> page);

	public Integer delete(Integer id);
}
