package com.dolphin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.core.dao.IBatisEntityDao;
import com.dolphin.domain.RoleItemAssign;

/**
 * RoleItemAssign Dao IBatis实现
 * 
 * @author: wanglf
 * @since: Jan 15, 2008 4:09:13 PM
 */

@Repository
public class RoleItemAssignDao extends IBatisEntityDao<RoleItemAssign> {

	public List<RoleItemAssign> queryByRoleId(Integer roleId) {
		Map map = new HashMap();
		map.put("roleId", roleId);

		return query(getEntityClass(), map);
	}
}
