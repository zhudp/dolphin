package com.core.dao.support;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 * 调用setCurrentPage,setCountPerPage.查询结果返回setData,setTotalCount
 * 对象引用，可以直接调传入的Page不用再new一个返回
 * 
 * @author wanglf
 * @since: Dec 19, 2007 9:38:45 PM
 */
@SuppressWarnings("serial")
public class Page implements Serializable {
	public static final String START = "start";
	public static final String LIMIT = "limit";

	private Object data; // 当前页中存放的记录,类型一般为List
	private int currentPage = 1;// 当前是第几页，从1开始记数
	private int countPerPage = 20;// 每页多少条
	private long totalCount; // 总记录数
	private int totalPageCount; // 总页数

	/**
	 * 构造方法，只构造空页.
	 */
	@SuppressWarnings("unchecked")
	public Page() {
		this(0, new ArrayList());
	}

	@SuppressWarnings("unchecked")
	public Page(int countPerPage) {
		this(0, new ArrayList());
		this.setCountPerPage(countPerPage);
	}

	/**
	 * 默认构造方法.
	 * 
	 * @param totalSize
	 *            数据库中总记录条数
	 * @param data
	 *            本页包含的数据
	 * 
	 */
	public Page(long totalCount, Object data) {
		this.totalCount = totalCount;
		this.data = data;
	}

	/**
	 * 取总记录数.
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 取当前页中的记录.
	 */
	public Object getResult() {
		return data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 取总页数时，由总条数和每页的条数计算 updated by yanghb
	 */
	public int getTotalPageCount() {
		totalPageCount = ((int) totalCount / countPerPage);
		totalPageCount += ((totalCount % countPerPage) > 0) ? 1 : 0;
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

}