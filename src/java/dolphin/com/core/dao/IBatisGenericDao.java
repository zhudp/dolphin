package com.core.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import com.core.BaseEntity;
import com.core.dao.support.Page;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * IBatis Dao的泛型基类. </p>
 * 继承于Spring的SqlMapClientDaoSupport,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 * 
 * @author: wanglf
 * @since: Dec 19, 2007 1:36:06 PM
 */

/** 
 * @author: yanghb
 * @since: 2010-1-13  下午04:12:28
 * @history:
 ************************************************
 * @file: IBatisGenericDao.java
 * @Copyright: 2009 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************/
@SuppressWarnings("unchecked")
public abstract class IBatisGenericDao{
	public static final String POSTFIX_INSERT = "_insert";
	public static final String POSTFIX_UPDATE = "_update";
	public static final String POSTFIX_UPDATE_SELECTIVE = "_updateSelective";
	public static final String POSTFIX_DELETE = "_delete";
	public static final String POSTFIX_DELETE_BYKEY = "_deleteByPrimaryKey";
	public static final String POSTFIX_GET = "_get";
	public static final String POSTFIX_SELECT = "_list";
	public static final String POSTFIX_COUNT = "_count";
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	// -------------------------------------------------------------------------
	// CRUD
	/**
	 * 根据ID获取对象.
	 * 
	 * @throws 如果对象不存在
	 *             ，则抛出ObjectRetrievalFailureException异常.
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		Assert.notNull(id);
		T o = (T) getSqlMapClientTemplate().queryForObject(
				entityClass.getSimpleName() + POSTFIX_GET, id);
		//if (o == null)
		//	throw new ObjectRetrievalFailureException(entityClass, id);
		return o;
	}

	/**
	 * 获取全部对象.
	 */
	public <T> List<T> getAll(Class<T> entityClass) {
		return getSqlMapClientTemplate().queryForList(
				entityClass.getSimpleName() + POSTFIX_SELECT, null);
	}

	/**
	 * 新增对象.
	 * 
	 * @return Serializable 新增后生成的主键值.
	 */
	public Serializable insert(BaseEntity o) {
		Assert.notNull(o);
		return (Serializable) getSqlMapClientTemplate().insert(
				o.getClass().getSimpleName() + POSTFIX_INSERT, o);
	}

	/**
	 * 更新对象.
	 */
	public int update(BaseEntity o) {
		Assert.notNull(o);
		return getSqlMapClientTemplate().update(
				o.getClass().getSimpleName() + POSTFIX_UPDATE, o);
	}

	/**
	 * 更新对象.
	 */
	public int updateSelective(BaseEntity o) {
		Assert.notNull(o);
		return getSqlMapClientTemplate().update(
				o.getClass().getSimpleName() + POSTFIX_UPDATE_SELECTIVE, o);
	}

	/**
	 * 删除对象.
	 */
	public int remove(BaseEntity o) {
		Assert.notNull(o);
		return getSqlMapClientTemplate().delete(
				o.getClass().getSimpleName() + POSTFIX_DELETE, o);
	}
	
	/**
	 * 根据主键删除对象.
	 */
	public <T> T removeById(Class<T> entityClass, Serializable id) {
		Assert.notNull(id);
		getSqlMapClientTemplate().delete(entityClass.getSimpleName() + POSTFIX_DELETE_BYKEY, id);
		return null;
	}

	// ------------------------------------------------------------------ QUERY
	// & PAGE
	/**
	 * map查询.
	 * 
	 * @param map
	 *            包含各种属性的查询
	 */
	public <T> List<T> query(Class<T> entityClass, Map map) {
		Assert.notNull(map);
//		map.put("findBy", "True");
		return this.getSqlMapClientTemplate().queryForList(
				entityClass.getSimpleName() + POSTFIX_SELECT, map);
	}
	
	
	/**
	 * 带分页的查询
	 * @param <T>
	 * @param entityClass 类型
	 * @param map 参数
	 * @param start 查询开始记录
	 * @param limit 查询记录总数
	 * @return
	 */
	public <T> List<T> query(Class<T> entityClass, Map map, int start, int limit) {
		Assert.notNull(map);
		return this.getSqlMapClientTemplate().queryForList(
				entityClass.getSimpleName() + POSTFIX_SELECT, map, start, limit);
	}

	
	/**
	 * map查询.
	 * 
	 * @param map
	 *            包含各种属性的查询
	 */
	public <T> List<T> query(Class<T> entityClass, String statement, Map map) {
		Assert.notNull(statement);
		Assert.notNull(map);
		map.put("findBy", "True");
		return this.getSqlMapClientTemplate().queryForList(
				entityClass.getSimpleName() + statement, map);
	}

	/**
	 * map查询唯一值.
	 * 
	 * @param map
	 *            包含各种属性的查询
	 */
	public <T> T queryUniquely(Class<T> entityClass, Map map) {
		Assert.notNull(map);
		map.put("findBy", "True");
		return (T) getSqlMapClientTemplate().queryForObject(
				entityClass.getSimpleName() + POSTFIX_SELECT, map);
	}

	/**
	 * 得到符合查询条件的记录数
	 */
	public Integer getCount(Class entityClass, Object parameterObject) {
		Integer totalCount = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(entityClass.getSimpleName() + POSTFIX_COUNT,
						parameterObject);
		return totalCount;
	}

	/**
	 * map查询唯一值.
	 * 
	 * @param map
	 *            包含各种属性的查询
	 */
	public <T> T queryUniquely(Class<T> entityClass, String statement, Map map) {
		Assert.notNull(statement);
		Assert.notNull(map);
		map.put("findBy", "True");
		return (T) getSqlMapClientTemplate().queryForObject(
				entityClass.getSimpleName() + statement, map);
	}

	/**
	 * 后台分页查询.(javabean继承的BaseEntity类中已包含page相关参数)
	 * 
	 * @param start
	 *            当前页开始记录索引.
	 * @param limit
	 *            每页显示记录数.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page queryPaged(Class entityClass,
			Map<String, String> parameterObject) {
		int start = Integer.valueOf(parameterObject.get(Page.START)); // 从第几条数据开始取
		int limit = Integer.valueOf(parameterObject.get(Page.LIMIT)); // 总共取出几条数据用以显示
		return queryPaged(entityClass, parameterObject, start, limit);
	}

	/**
	 * 后台分页查询
	 */
	public Page queryPaged(Class entityClass, Object parameterObject,
			int start, int limit) {
		Assert.isTrue(start >= 0, "start should start from 0");
		Assert.isTrue(limit >= 0, "limit should more than 0");
		List list;
		// int pageNo = start ;
		// 计算总数
		Integer totalCount = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(entityClass.getSimpleName() + POSTFIX_COUNT,
						parameterObject);
		/*
		 * // 如果没有数据则返回Empty Page Assert.notNull(totalCount,
		 * "totalCount Error"); if (totalCount.intValue() == 0) { return new
		 * Page(); } int totalPageCount = 0; int startIndex = 0; //
		 * 如果pageSize小于0,则返回所有数据,等同于getAll if (limit > 0) { // 计算页数
		 * totalPageCount = (totalCount / limit); totalPageCount += ((totalCount
		 * % limit) > 0) ? 1 : 0; // 计算skip数量 if (totalPageCount > pageNo) {
		 * startIndex = (pageNo - 1) limit; } else { startIndex =
		 * (totalPageCount - 1) limit; }
		 */
		if (limit > 0) {
			
			// 2012-11-08 zhudp 对表记录数大于2万，获取超过20页的的查询，使用oracle分布机制
			if(totalCount > 20000 && start > 200 && parameterObject instanceof Map){
				Map parameterMap = (Map)parameterObject;
				parameterMap.put("bigPage", "true");
				parameterMap.put("bigPageOpen", "select * from (select row_.*, rownum rownum_ from (");
				parameterMap.put("bigPageClose", ") row_ where rownum <= "+(start+limit)+") where rownum_ > "+start);
				
				list = getSqlMapClientTemplate().queryForList(
						entityClass.getSimpleName() + POSTFIX_SELECT,
						parameterMap);
			}else {
				list = getSqlMapClientTemplate().queryForList(
						entityClass.getSimpleName() + POSTFIX_SELECT,
						parameterObject, start, limit);
			}

		} else {
			list = getSqlMapClientTemplate().queryForList(
					entityClass.getSimpleName() + POSTFIX_SELECT,
					parameterObject);
		}
		Page page = new Page(totalCount, list);
		page.setCountPerPage(limit);
		page.setCurrentPage(start);
		return page;
		
		
	}

	/**
	 * 前台分页查询(javabean未继承BaseEntity类,相关的page要初始化Page类后才传入). page分页参数
	 * 
	 * @param entityClass
	 * @param parameterObject
	 *            查询条件MAP
	 * @param page
	 *            返回数据的PAGA类,带分页信息
	 * @return PAGA类
	 */
	public Page queryByPage(Class entityClass,
			Map<String, String> parameterObject, Page page) {
		return queryByPage(POSTFIX_SELECT, entityClass, parameterObject, page);
	}

	/**
	 * 前台分页查询. page分页参数
	 */
	public Page queryByPage(String statement, Class entityClass,
			Map<String, String> parameterObject, Page page) {
		int pageNo = page.getCurrentPage();// 当前显示第几页,从1开始
		int limit = page.getCountPerPage();// 每页显示多少条
		List list;
		int totalPageCount = 0;// 数据库中总共有多少页记录集
		int startIndex = 0;// 当前从第几条开始显示
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		Assert.isTrue(limit > 0, "limit should more than 0");
		// 计算数据库中记录集总数
		Integer totalCount = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(entityClass.getSimpleName() + POSTFIX_COUNT,
						parameterObject);
		// 如果没有数据则返回Empty Page
		Assert.notNull(totalCount, "totalCount Error");
		if (totalCount.intValue() == 0) {
			page.setData(null);
			return page;
		}
		// 如果pageSize小于0,则返回所有数据,等同于getAll
		if (limit > 0) {
			// 计算页数
			totalPageCount = (totalCount / limit);
			totalPageCount += ((totalCount % limit) > 0) ? 1 : 0;
			// 计算skip数量
			if (totalPageCount > pageNo) {
				startIndex = (pageNo - 1) * limit;
			} else {
				startIndex = (totalPageCount - 1) * limit;
				pageNo = totalPageCount;
			}
			list = getSqlMapClientTemplate().queryForList(
					entityClass.getSimpleName() + statement, parameterObject,
					startIndex, limit);
		} else {
			list = getSqlMapClientTemplate().queryForList(
					entityClass.getSimpleName() + statement, parameterObject);
		}
		page.setTotalCount(totalCount);
		page.setTotalPageCount(totalPageCount);
		page.setData(list);
		page.setCurrentPage(pageNo);
		return page;
	}

	// -------------------------------------------------------------------------
	// BATCH
	/**
	 * 批量插入.
	 */
	public <T> List<T> insertBatch(final List<T> list) {
		batch(list, POSTFIX_INSERT);
		return null;
	}

	/**
	 * 批量更新.
	 */
	public <T> List<T> updateBatch(final List<T> list) {
		batch(list, POSTFIX_UPDATE);
		return null;
	}

	/**
	 * 批量更新.只更新不为null的字段
	 */
	public <T> List<T> updateSelectiveBatch(final List<T> list) {
		batch(list, POSTFIX_UPDATE_SELECTIVE);
		return null;
	}

	/**
	 * 批量删除.
	 */
	public <T> List<T> removeBatch(final List<T> list) {
		batch(list, POSTFIX_DELETE);
		return null;
	}

	private <T> List<T> batch(final List<T> list, final String executeType) {
		Assert.notNull(list);
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				if (executeType.equals(POSTFIX_INSERT))
					for (int i = 0; i < list.size(); i++) {
						T o = list.get(i);
						executor.insert(o.getClass().getSimpleName()
								+ POSTFIX_INSERT, o);
					}
				if (executeType.equals(POSTFIX_UPDATE))
					for (int i = 0; i < list.size(); i++) {
						T o = list.get(i);
						executor.update(o.getClass().getSimpleName()
								+ POSTFIX_UPDATE, o);
					}
				if (executeType.equals(POSTFIX_UPDATE_SELECTIVE))
					for (int i = 0; i < list.size(); i++) {
						T o = list.get(i);
						executor.update(o.getClass().getSimpleName()
								+ POSTFIX_UPDATE_SELECTIVE, o);
					}
				if (executeType.equals(POSTFIX_DELETE))
					for (int i = 0; i < list.size(); i++) {
						T o = list.get(i);
						executor.delete(o.getClass().getSimpleName()
								+ POSTFIX_DELETE, o);
					}
				executor.executeBatch();
				return null;
			}
		});
		return null;
	}

	/**
	 * 门户分页查询函数.
	 * @param parameterObject 查询条件
	 * @param start 当前页开始记录索引.
	 * @param limit 每页显示记录数.
	 * @param sqmap sqlMap.xml中对应配置方法前面的类名.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page getPage(Object parameterObject, int start, int limit,
			String sqmap) {
		Assert.isTrue(start >= 0, "start should start from 0");
		Assert.isTrue(limit >= 0, "limit should more than 0");
		List list = new ArrayList();
		int pageNo = start / limit + 1;
		// 计算总数
		Integer totalCount = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(sqmap + POSTFIX_COUNT, parameterObject);
		// 如果没有数据则返回Empty Page
		Assert.notNull(totalCount, "totalCount Error");
		if (totalCount.intValue() == 0) {
			return new Page();
		}
		int totalPageCount = 0;
		int startIndex = 0;
		// 如果pageSize小于0,则返回所有数据,等同于getAll
		if (limit > 0) {
			// 计算页数
			totalPageCount = (totalCount / limit);
			totalPageCount += ((totalCount % limit) > 0) ? 1 : 0;
			// 计算skip数量
			if (totalPageCount > pageNo) {
				startIndex = (pageNo - 1) * limit;
			} else {
				startIndex = (totalPageCount - 1) * limit;
			}
			if (limit > 0) {
				list = getSqlMapClientTemplate().queryForList(sqmap + POSTFIX_SELECT,
						parameterObject, startIndex, limit);
			} else {
				list = getSqlMapClientTemplate().queryForList(sqmap + POSTFIX_SELECT,
						parameterObject);
			}
		}
		Page page = new Page(totalCount, list);
		page.setCountPerPage(limit);
		page.setCurrentPage(start);
		page.setTotalPageCount(totalPageCount);
		return page;
	}
}
