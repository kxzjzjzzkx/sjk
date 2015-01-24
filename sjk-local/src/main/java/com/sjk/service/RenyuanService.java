/**
 * @author kongsj
 * @date 2015年1月9日
 * 
 */
package com.sjk.service;

import java.io.UnsupportedEncodingException;

import com.sjk.domain.PageDto;
import com.sjk.domain.Renyuan;

public interface RenyuanService {
	public Integer create(Renyuan renyuan);
	
	public PageDto<Renyuan> page(Renyuan renyuan,PageDto<Renyuan> page) throws UnsupportedEncodingException;
	
	public Renyuan queryById(Integer id);
	
	public Integer update(Renyuan renyuan);
	
	public Integer delete(Integer id);
}
