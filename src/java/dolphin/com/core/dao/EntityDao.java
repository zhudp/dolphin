package com.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.core.BaseEntity;
import com.core.dao.support.Page;

/**
 * 针对单个Entity对象的操作定义.不依赖于具体ORM实现方案.
 * @author: wanglf
 * @since: Dec 19, 2007 9:38:45 PM
 */
public interface EntityDao<T> {
    /**
     * 根据id获取单条记录.
     * @see IBatisEntityDao#get(Serializable)
     */
    public T get(Serializable id);

    /**
     * 获取所有记录.
     * @see IBatisEntityDao#getAll()
     */
    public List<T> getAll();

    /**
     * 插入新记录.
     * @see IBatisGenericDao#insert(BaseEntity)
     */
    public Serializable insert(BaseEntity o);

    
    /**
     * 得到符合查询条件的记录数
     */
    public Integer getCount(Map<String, String> parameterObject) ;
    
    /**
     * 更新记录.
     * @see IBatisGenericDao#update(BaseEntity)
     */
    public int update(BaseEntity o);

    /**
     * 批量插入.
     * @see IBatisGenericDao#insertBatch(List)
     */
    public void insertBatch(List<T> list);

    /**
     * 更新记录.
     * @see IBatisGenericDao#updateSelective(BaseEntity)
     */
    public int updateSelective(BaseEntity o);

    /**
     * 删除记录.
     * @see IBatisGenericDao#remove(BaseEntity)
     */
    public int remove(BaseEntity o);

    /**
     * 批量删除
     */
    public void removeBatch(List<T> list);
    
    /**
     * 批量更新
     */
    public void updateBatch(List<T> list);
    
    /**
     * 批量更新.只更新不为null的字段
     */
    public void updateSelectiveBatch(List<T> list);
    
    /**
     * map中包含对象各属性参数
     * 查询记录列表
     */
    public List<T> query(Map map);
    
    /**
     * map中包含对象各属性参数
     * statement是ibatis中sql语句名称的后缀
     * 查询记录列表
     */
    public List<T> query(String statement, Map map);
    
    /**
     * 后台分页查询用户列表.
     * <p>
     * 多用于单表查询.
     * @param parameterObject 查询参数对象，实体类对象.
     * @return Page
     *         <li>getResult 实体类列表
     *         <li>getTotalCount 数据库中总记录条数
     * @see IBatisEntityDao#queryPaged(BaseEntity, int, int)
     */
    public Page queryPaged(T parameterObject, int start, int limit);

    /**
     * 后台分页查询.(javabean继承的BaseEntity类中已包含page相关参数)
     * <p>
     * 多用于关联查询.
     * @param parameterObject 查询参数对象，Map对象.
     * @return Page
     *         <li>getResult 本页包含的数据
     *         <li>getTotalCount 数据库中总记录条数
     * @see IBatisEntityDao#queryPaged(Map)
     */
    public Page queryPaged(Map<String, String> parameterObject);
    
    /**
	 * 前台分页查询.
	 * page分页参数
	 */
	public Page queryByPage(String statement, Map<String, String> parameterObject,Page page) ;
	
	/**
	 * 前台分页查询.
	 * page分页参数
	 */
	public Page queryByPage(Map<String, String> parameterObject,Page page) ;
	
	/**
	 * 门户分页查询函数.
	 * @param start 当前页开始记录索引.
	 * @param limit 每页显示记录数.
	 * @param parameterObject 查询条件
	 * @param sqmap 调用xml文件中方法前面的类名
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page getPage(Object parameterObject, int start, int limit, String sqmap);
}
