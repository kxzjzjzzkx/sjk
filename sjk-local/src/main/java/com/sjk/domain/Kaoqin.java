/**
 * @author kongsj
 * @date 2015年1月15日
 * 
 */
package com.sjk.domain;

import java.util.Date;

public class Kaoqin {

	private Integer id;
	private int rid;
	private String rname;
	private Date gmtWork; // 工作日期
	private Float timeWork; //工作时长
	private Date gmtCreated;
	private Date gmtModified;
	private String gmtWorkStr;

	private String from;
	private String to;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public Date getGmtWork() {
		return gmtWork;
	}

	public void setGmtWork(Date gmtWork) {
		this.gmtWork = gmtWork;
	}

	public Float getTimeWork() {
		return timeWork;
	}

	public void setTimeWork(Float timeWork) {
		this.timeWork = timeWork;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getGmtWorkStr() {
		return gmtWorkStr;
	}

	public void setGmtWorkStr(String gmtWorkStr) {
		this.gmtWorkStr = gmtWorkStr;
	}

	
	
}
