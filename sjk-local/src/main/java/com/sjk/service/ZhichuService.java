/**
 * @author kongsj
 * @date 2015年1月13日
 * 
 */
package com.sjk.service;

import com.sjk.domain.PageDto;
import com.sjk.domain.Zhichu;

public interface ZhichuService {

	public Integer insert(Zhichu zhichu);

	public Zhichu queryById(Integer id);

	public Integer update(Zhichu zhichu);

	public PageDto<Zhichu> page(Zhichu zhichu, PageDto<Zhichu> page);
	
	public Integer delete(Integer id);
}
