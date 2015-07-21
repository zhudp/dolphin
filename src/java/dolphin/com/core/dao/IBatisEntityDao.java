package com.core.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.core.BaseEntity;
import com.core.dao.support.Page;
import com.core.utils.GenericsUtils;

/**
 * 负责为单个Entity 提供CRUD操作的IBatis DAO基类.
 * </p>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作.
 * 
 * <pre>
 * public class UserDaoIbatisImp extends IBatisEntityDao&lt;User&gt; implement UserDao {
 * }
 * </pre>
 * 
 * @author: wanglf
 * @since: Dec 19, 2007 4:49:39 PM
 */
public abstract class IBatisEntityDao<T extends BaseEntity> extends IBatisGenericDao {
	/**
	 * DAO所管理的Entity类型.
	 */
	protected Class<T> entityClass;

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	@SuppressWarnings("unchecked")
	public IBatisEntityDao() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 根据ID获取对象.
	 */
	public T get(Serializable id) {
		return get(getEntityClass(), id);
	}
	
	/**
	 * 根据ID删除对象.
	 */
	public T removeById(Serializable id) {
		return removeById(getEntityClass(), id);
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> getAll() {
		return getAll(getEntityClass());
	}

	/**
     * 得到符合查询条件的记录数
     */
    public Integer getCount(Map<String, String> parameterObject) {
    	return getCount(getEntityClass(), parameterObject);
    }
    
	 /**
     * 查询记录列表
     */
    public List<T> query(Map map) {
    	return query(getEntityClass() ,map);
    }
    
    /**
     * map中包含对象各属性参数
     * statement是ibatis中sql语句名称的后缀
     * 查询记录列表
     */
    public List<T> query(String statement, Map map){
    	return query(getEntityClass() , statement, map);
    }
    
	/**
	 * 分页查询.
	 */
	public Page queryPaged(Map<String, String> parameterObject) {
		return queryPaged(getEntityClass(), parameterObject);
	}

	
	/** 
	 * EXT 自定义SQL 分页查询
	 * @param parameterMap
	 * @param ststement sql语句名称
	 * @return 
	* @create  2010-10-13 下午01:16:10 guozq
	* @history  
	*/
	public Page queryPaged(Map<String, String> parameterMap,String statement) {
		int start = Integer.valueOf(parameterMap.get(Page.START)); // 从第几条数据开始取
		int limit = Integer.valueOf(parameterMap.get(Page.LIMIT)); // 总共取出几条数据用以显示
		Page page = new Page();
		int pageNo = 1;// 当前显示第几页,从1开始
		List list = new ArrayList();;
		int totalPageCount = 0;// 数据库中总共有多少页记录集
		Assert.isTrue(limit > 0, "limit should more than 0");
		Assert.isTrue(start >= 0, "start should more than 0");
		// 计算数据库中记录集总数
		Integer totalCount = (Integer) this.getSqlMapClientTemplate().queryForObject(statement + POSTFIX_COUNT, parameterMap);
		// 如果没有数据则返回Empty Page
		Assert.notNull(totalCount, "totalCount Error");
		if (totalCount.intValue() == 0) {
			page.setData(list);
			return page;
		}
		// 如果pageSize小于0,则返回所有数据,等同于getAll
		if (limit > 0) {
			// 计算总页数
			totalPageCount = (totalCount / limit);
			totalPageCount += ((totalCount % limit) > 0) ? 1 : 0;
			// 计算当前页数
			pageNo = start/limit + 1;
			list = getSqlMapClientTemplate().queryForList(statement, parameterMap,start, limit);
		} else {
			list = getSqlMapClientTemplate().queryForList(statement, parameterMap);
		}
		page.setTotalCount(totalCount);
		page.setTotalPageCount(totalPageCount);
		page.setData(list);
		page.setCurrentPage(pageNo);
		return page;
	}
	
	/** 
	 * EXT 自定义SQL 分页查询
	 * @param parameterMap
	 * @param ststement sql语句名称
	 * @return 
	* @create  2010-10-13 下午01:16:10 guozq
	* @history  
	*/
	public Page queryPaged(Map<String, Object> parameterMap,String statement,boolean a) {
		
		int start = Integer.valueOf(parameterMap.get(Page.START).toString()); // 从第几条数据开始取
		int limit = Integer.valueOf(parameterMap.get(Page.LIMIT).toString()); // 总共取出几条数据用以显示
		Page page = new Page();
		int pageNo = 1;// 当前显示第几页,从1开始
		List list = new ArrayList();;
		int totalPageCount = 0;// 数据库中总共有多少页记录集
		Assert.isTrue(limit > 0, "limit should more than 0");
		Assert.isTrue(start >= 0, "start should more than 0");
		// 计算数据库中记录集总数
		Integer totalCount = (Integer) this.getSqlMapClientTemplate().queryForObject(statement + POSTFIX_COUNT, parameterMap);
		// 如果没有数据则返回Empty Page
		Assert.notNull(totalCount, "totalCount Error");
		if (totalCount.intValue() == 0) {
			page.setData(list);
			return page;
		}
		// 如果pageSize小于0,则返回所有数据,等同于getAll
		if (limit > 0) {
			// 计算总页数
			totalPageCount = (totalCount / limit);
			totalPageCount += ((totalCount % limit) > 0) ? 1 : 0;
			// 计算当前页数
			pageNo = start/limit + 1;
			list = getSqlMapClientTemplate().queryForList(statement, parameterMap,start, limit);
		} else {
			list = getSqlMapClientTemplate().queryForList(statement, parameterMap);
		}
		page.setTotalCount(totalCount);
		page.setTotalPageCount(totalPageCount);
		page.setData(list);
		page.setCurrentPage(pageNo);
		return page;
	}

	/**
	 * 分页查询.
	 */
	public Page queryPaged(T parameterObject, int start, int limit) {
		return queryPaged(getEntityClass(), parameterObject, start, limit);
	}
	
	/**
	 * 分页查询.
	 * page分页参数
	 */
	public Page queryByPage(Map<String, String> parameterObject,Page page) {
		return queryByPage(getEntityClass(), parameterObject,page);
	}
	/**
	 * 分页查询.
	 * page分页参数
	 */
	public Page queryByPage(String statement, Map<String, String> parameterObject,Page page) {
	    return queryByPage(statement, getEntityClass(), parameterObject,page);
	}

	/**
	 * 取得entityClass.
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	
}
