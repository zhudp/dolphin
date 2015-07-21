package com.dolphin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.core.dao.IBatisEntityDao;
import com.dolphin.domain.Role;
import com.dolphin.domain.UserRoleAssign;
import com.dolphin.domain.UserSecurity;

/**
 * UserRoleAssign Dao IBatis实现
 * 
 * @author: wanglf
 * @since: Jan 15, 2008 4:09:13 PM
 */
@Repository
@SuppressWarnings("unchecked")
public class UserRoleAssignDao extends IBatisEntityDao<UserRoleAssign>{
   
	public List<UserRoleAssign> queryByUserId(Integer userId) {
		Map map = new HashMap();
		map.put("userId", userId);
		// 过滤掉已失效的角色     by chenzy 2010-11-03
        map.put("status", UserSecurity.STATUS_INIT);
		return query(getEntityClass(), map);
	}

	public UserRoleAssign queryById(Integer userId, Integer roleId) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("roleId", roleId);

		return queryUniquely(getEntityClass(), map);
	}
}
