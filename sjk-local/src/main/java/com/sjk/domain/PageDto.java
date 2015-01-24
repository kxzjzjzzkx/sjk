package com.sjk.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author mays@caiban.net 用于Ext表格分页时返回的数据信息 格式:
 *         {"pageSize":20,"recoresReturned"
 *         :100,"totalRecords":100,"startIndex":0,"sort":"id","dir":"asc",
 *         records:[]}
 */
public class PageDto<T> implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/** 默认分页记录量 **/
	private final static int DEFAULT_SIZE = 20;
	/** 默认排序方式［降序］ **/
	private final static String DEFAULT_DIR = "desc";
	
	/** 排序字段 **/
	private String sort;
	/** 排序方式[desc,asc] **/
	private String dir;
	/** 页面记录开始索引 **/
	private Integer startIndex ;
	/** 分页大小 **/
	private Integer pageSize;
	/** 记录总数量 **/
	private Integer totalRecords = 0;
	/** 返回到页面的数据量 **/
	private Integer recordsReturned;

	/** 分页总数，通过计算获得，不能直接赋值 **/
	private Integer totalPages = 0;
	/** 当前页 **/
	private Integer currentPage = 1;
	/** 记录集 **/
	private List<T> records;
	
	private String searchOrderByAndDir;

	public PageDto() {
	}

	public PageDto(int pageSize) {
		if (pageSize <= 0)
			pageSize = DEFAULT_SIZE;
		this.pageSize = pageSize;
	}

	public PageDto(int pageSize, String sort, String dir) {
		this(pageSize);
		this.sort = sort;
		this.dir = dir;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		if (dir == null) {
			dir = DEFAULT_DIR;
		}
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getStartIndex() {
		if (startIndex == null) {
			startIndex = 0;
		}
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getPageSize() {
		if (pageSize == null) {
			pageSize = DEFAULT_SIZE;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getTotalPages() {
		totalPages = totalRecords / pageSize + 1;
		if (totalRecords % pageSize == 0) {
			totalPages--;
		}
		return totalPages;
	}

	public Integer getRecordsReturned() {
		return recordsReturned;
	}

	public void setRecordsReturned(Integer recordsReturned) {
		this.recordsReturned = recordsReturned;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public String getSearchOrderByAndDir() {
		return searchOrderByAndDir;
	}

	public void setSearchOrderByAndDir(String searchOrderByAndDir) {
		this.searchOrderByAndDir = searchOrderByAndDir;
	}

	
}
