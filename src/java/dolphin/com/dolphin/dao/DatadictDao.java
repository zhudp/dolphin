/**
 * 
 */
package com.dolphin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.core.dao.IBatisEntityDao;
import com.dolphin.domain.Datadict;

/** 
 ************************************************
 * @file: DatadictDaoIBatis.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************
 * @package: com.hs.common.dao.ibatis
 * @class: DatadictDaoIBatis
 * @description: 
 * 
 * @author: lilq
 * @since: 2008-5-26-11:22:06
 * @history:
 **/
@Repository
@SuppressWarnings("unchecked")
public class DatadictDao extends IBatisEntityDao<Datadict> {

	public List<Datadict> queryByParentId(Long parentId) {
		Map map = new HashMap();
		map.put("parentId", parentId);

		return query(getEntityClass(), map);
	}
	
	public List<Datadict> queryByResType(String resType) {
		Map map = new HashMap();
		map.put("resType", resType);
		return query(getEntityClass(), map);
	}
	
	
	public List<Datadict> queryData(String resType) {
		Map map = new HashMap();
		map.put("resType", resType);
		map.put("parentId", "0");
		return getSqlMapClientTemplate().queryForList(
				"Datadict_data_list", map);
	}
	
	public List<Datadict> queryAllByParentId(String parentId) {
		return getSqlMapClientTemplate().queryForList(
				"Datadict_AllChrildList", parentId);
	}
	
   public List<Datadict> queryByLevel(Integer level) {
		Map map = new HashMap();
		map.put("codeLevel", level);

		return query(getEntityClass(), map);
	}

	public List<Datadict> queryByMarket(Datadict datadict) {
		return getSqlMapClientTemplate().queryForList(
				"Datadict_list", datadict);
	}
	public List<Datadict> queryByParentId(HashMap<String, Long> map) {
		return query(getEntityClass(), map);
	}
	public Integer getDataDictTopLevelId() {
		return (Integer)getSqlMapClientTemplate().queryForObject("getDataDictTopLevelId");
	}
	
	public List<Datadict> queryAllByHotkeyCode(String hotkeyCode) {
		return getSqlMapClientTemplate().queryForList(
				"Datadict_getDatadictIdList", hotkeyCode);
	}
	
 }
