package com.core;

import java.io.Serializable;

/**
 * Entity基类.
 * 
 * @author: wanglf
 * @since: Dec 19, 2007 3:23:08 PM
 */
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 2734951563954109759L;
	// 分页查询
	private int start;
	private int limit;

	// 字段排序
	private String sort; // 字段
	private String dir; // 升序Or降序

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}
