package com.dolphin.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.core.dao.IBatisEntityDao;
import com.dolphin.domain.UserSecurity;

/**
 * UserSecurity Dao IBatis实现
 * 
 * @author: wanglf
 * @since: Jan 15, 2008 4:09:13 PM
 */
@Repository
@SuppressWarnings("unchecked")
public class UserSecurityDao extends IBatisEntityDao<UserSecurity>{

	public UserSecurity queryByAccount(String account) {
		Map map = new HashMap();
		map.put("account", account);

		return queryUniquely(entityClass, map);
	}
	
	public UserSecurity getByUserId (Integer userId){ 
		return (UserSecurity)getSqlMapClientTemplate().queryForObject("UserSecurity_getByUserId", userId);
	}
}
