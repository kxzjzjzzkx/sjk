/**
 * @author kongsj
 * @date 2015年1月9日
 * 
 */
package com.sjk.domain;

import java.io.Serializable;
import java.util.Date;

public class Renyuan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 594359316449617620L;

	private Integer id;
	private String username;
	private Date gmtIn;
	private int usermoney;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUsermoney() {
		return usermoney;
	}

	public void setUsermoney(int usermoney) {
		this.usermoney = usermoney;
	}

	public Date getGmtIn() {
		return gmtIn;
	}

	public void setGmtIn(Date gmtIn) {
		this.gmtIn = gmtIn;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
