/**
 * @author kongsj
 * @date 2015年1月13日
 * 
 */
package com.sjk.domain;

import java.util.Date;

public class Zhichu {
	private int id;
	private int rid; // 员工id
	private Date gmtGet; // 取钱日期
	private int moneyGet; // 取钱金额
	private String remark; // 备注
	private String rname; // 员工姓名
	private Date gmtCreated;
	private Date gmtModified;

	private String from;
	private String to;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public Date getGmtGet() {
		return gmtGet;
	}

	public void setGmtGet(Date gmtGet) {
		this.gmtGet = gmtGet;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
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

	public int getMoneyGet() {
		return moneyGet;
	}

	public void setMoneyGet(int moneyGet) {
		this.moneyGet = moneyGet;
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

}
