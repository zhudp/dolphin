package com.dolphin.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dolphin.dao.UserRoleAssignDao;
import com.dolphin.domain.RoleItemAssign;
import com.dolphin.domain.UserRoleAssign;

@Service
@Transactional
public class UserRoleService {
	@Autowired
	private UserRoleAssignDao userRoleAssignDao;
	@Autowired
	private RoleService roleService;

	public Integer[] getUserRoles(Integer userId) {
		List<UserRoleAssign> roles = userRoleAssignDao.queryByUserId(userId);

		Integer[] roleIds = new Integer[roles.size()];

		int i = 0;
		for (UserRoleAssign role : roles) {
			roleIds[i] = role.getRoleId();
			i++;
		}

		return roleIds;
	}

	public Integer[] getUserProxyRoles(Integer userId, Integer proxyUserId) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("proxyUserId", proxyUserId);
		List<UserRoleAssign> roles = userRoleAssignDao.query(map);

		Integer[] roleIds = new Integer[roles.size()];

		int i = 0;
		for (UserRoleAssign role : roles) {
			roleIds[i] = role.getRoleId();
			i++;
		}

		return roleIds;
	}

	public void userRoleSave(Integer userId, String roleIdsStr) {
		UserRoleAssign userRoleAssign = new UserRoleAssign();
		Integer noProxy = Integer.valueOf(-1);
		userRoleAssign.setUserId(userId);
		userRoleAssign.setProxyUserId(noProxy);
		userRoleAssignDao.remove(userRoleAssign);
		String[] roleIds = roleIdsStr.split(",");

		for (String roleId : roleIds) {
			userRoleAssign.setRoleId(Integer.valueOf(roleId));
			userRoleAssign.setProxyUserId(noProxy);
			userRoleAssignDao.insert(userRoleAssign);
		}
	}

	/**
	 * 代理权限分配
	 * 
	 * @param userId
	 * @param roleIdsStr
	 * @param proxyUserId
	 * @create 2009-10-19 下午03:53:11 yanghb
	 * @history
	 */
	public void userProxyRoleSave(Integer userId, String roleIdsStr,
			Integer proxyUserId) throws Exception {
		UserRoleAssign userRoleAssign = new UserRoleAssign();
		userRoleAssign.setUserId(userId);
		userRoleAssign.setProxyUserId(proxyUserId);
		userRoleAssignDao.remove(userRoleAssign);

		String[] roleIds = roleIdsStr.split(",");

		List<String> list = roleService.getUserOwnResourceIdOrderList(userId);
		List<RoleItemAssign> roleIlist = null;
		boolean flag = false;
		for (String roleId : roleIds) {
			roleIlist = roleService.getRoleItems(Integer.valueOf(roleId));
			for (RoleItemAssign resource : roleIlist) {
				if (Collections.binarySearch(list, resource.getRoleItemId()) > -1) {
					throw new Exception("已经有其中的权限了，不需要代办！");
				}
			}
			userRoleAssign.setRoleId(Integer.valueOf(roleId));
			userRoleAssign.setProxyUserId(proxyUserId);
			userRoleAssignDao.insert(userRoleAssign);
		}
	}

}
