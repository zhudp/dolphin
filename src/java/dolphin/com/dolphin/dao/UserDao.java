package com.dolphin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.core.dao.IBatisEntityDao;
import com.dolphin.domain.User;

/**
 * 用户Dao IBatis实现
 *
 * @author: wanglf
 * @since: Jan 15, 2008 4:09:13 PM
 */
@Repository
public class UserDao extends IBatisEntityDao<User> {
	/**
	 * 根据主键id获取用户信息.
	 */
	public User queryByUserId(Integer id) {
		return super.get(id);
	}

	public List<User> getUserList(Map map){
		return super.query(getEntityClass(), map);
	}

	public List<User> getUserForCache() {
		return getSqlMapClientTemplate().queryForList("User_cache_list");
	}

	public Map  getMap(){
		return getSqlMapClientTemplate().queryForMap("User_cache_list", null, "id", "userName");
	}
}
