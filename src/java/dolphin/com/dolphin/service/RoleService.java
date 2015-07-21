package com.dolphin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.core.exception.BusinessException;
import com.dolphin.Constants;
import com.dolphin.dao.RoleDao;
import com.dolphin.dao.RoleItemAssignDao;
import com.dolphin.dao.UserRoleAssignDao;
import com.dolphin.domain.Role;
import com.dolphin.domain.RoleItemAssign;
import com.dolphin.domain.UserRoleAssign;
import com.dolphin.domain.resource.Resource;
import com.dolphin.spring.security.InvocationSecurityMetadataSourceService;

/**
 * 
 * @author Evan.Shen
 * 
 */
@Service
@Transactional
public class RoleService extends BaseService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleItemAssignDao roleItemAssignDao;
	@Autowired
	private UserRoleAssignDao userRoleAssignDao;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private InvocationSecurityMetadataSourceService invocationSecurityMetadataSourceService;
	

	public Role getRole(Integer id) {
		Assert.notNull(id);

		return roleDao.get(id);
	}

	public Integer insertRole(Role o) {
		Assert.notNull(o);
		//判断角色名是否存在     by chenzy 2010-11-04
		if(isRoleNameExist(o.getRoleName(), new Integer(0))){
			throw new BusinessException("User_0004",o.getRoleName());
		}
		return (Integer) roleDao.insert(o);
	}

	public void updateRole(Role o) {
		Assert.notNull(o);
		//判断角色名是否存在     by chenzy 2010-11-04
		if(isRoleNameExist(o.getRoleName(), o.getId())){
			throw new BusinessException("User_0004",o.getRoleName());
		}
		roleDao.update(o);
	}

	public void deleteRole(Role o) {
		Assert.notNull(o);

		roleDao.remove(o);
	}

	public Page queryRole(Map<String, String> parameterObject) {
		Assert.notNull(parameterObject);

		return roleDao.queryPaged(parameterObject);
	}

	public List<Role> getAll() {
		return roleDao.getAll();
	}

	public List<RoleItemAssign> getRoleItems(Integer roleId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("roleId", roleId);
		List<RoleItemAssign> list = roleItemAssignDao.query(map);
		return list;
	}

	public List<String> queryRoleItemsAssigned(Integer roleId) {
		List<RoleItemAssign> dbResults = roleItemAssignDao
				.queryByRoleId(roleId);
		List<String> o = new ArrayList<String>(dbResults.size());
		for (RoleItemAssign dbResult : dbResults)
			o.add(dbResult.getRoleItemId());

		return o;
	}

	public void saveRoleItemsAssigning(Integer roleId, String[] roleItemIds) {
		roleItemAssignDao.remove(new RoleItemAssign(roleId, null));
		if (roleItemIds != null) {
			List<RoleItemAssign> o = new ArrayList<RoleItemAssign>(
					roleItemIds.length);
			for (int i = 0; i < roleItemIds.length; i++) {
				if (StringUtils.isNotBlank(roleItemIds[i])) {
					o.add(new RoleItemAssign(roleId, roleItemIds[i]));
				}
			}
			roleItemAssignDao.insertBatch(o);
		}
		//修改角色分配时，重新加载所有资源权限项
		invocationSecurityMetadataSourceService.loadResourceDefine();
		
	}

	@SuppressWarnings("unchecked")
	public Page userRoleAssignQueryPaged(Map<String, String> parameterObject,
			Integer userId) {
		Page page = roleDao.queryPaged(parameterObject);

		List<UserRoleAssign> roleAssigned = userRoleAssignDao
				.queryByUserId(userId);
		for (UserRoleAssign assign : roleAssigned) {
			for (Role role : (List<Role>) page.getResult()) {
				if (role.getId().equals(assign.getRoleId())) {
					role.setUserAssined(Constants.WHETHER_YES);
					break;
				}
			}
		}

		return page;
	}

	public void userRoleAssign(UserRoleAssign o) {
		if (userRoleAssignDao.queryById(o.getUserId(), o.getRoleId()) == null) {
			userRoleAssignDao.insert(o);
		}
	}

	public List<Resource> getRoleMenusListByUserId(Integer userId) {
		Assert.notNull(userId, "userId不存在");

		return resourceService
				.getMenusListByIds(getResourceListByUserId(userId));
	}

	public List<String> getResourceListByUserId(Integer userId) {
		Assert.notNull(userId, "userId不存在");

		List<RoleItemAssign> roleItemAssignList = getAllRoleResourceListByUserId(userId);
		return buildResourceIdListByRoleItemAssignList(roleItemAssignList);
	}

	public List<String> getUserOwnResourceIdOrderList(Integer userId) {
		Assert.notNull(userId, "userId不存在");

		List<String> userOwnResourceIdList = getResourceListByUserId(userId);
		Collections.sort(userOwnResourceIdList);
		return userOwnResourceIdList;
	}

	public List<String> getRoleOwnResourceIdOrderList(Integer roleId) {
		Assert.notNull(roleId, "roleId不存在");

		List<String> roleOwnResourceIdList = buildResourceIdListByRoleItemAssignList(roleItemAssignDao
				.queryByRoleId(roleId));
		Collections.sort(roleOwnResourceIdList);
		return roleOwnResourceIdList;
	}

	public String getMainResourceByRoleIdWithChecked(Integer roleId) {
		Assert.notNull(roleId, "roleId不存在");

		return resourceService
				.getTreeFromMenusWithChecked(getRoleOwnResourceIdOrderList(roleId));
	}

	public String getUrlMainResourceMapJsByUserId(Integer userId) {
		Assert.notNull(userId, "userId不存在");

		return resourceService
				.getUrlMainResourceMapJS(getUserOwnResourceIdOrderList(userId));
	}

	private List<String> buildResourceIdListByRoleItemAssignList(
			List<RoleItemAssign> roleItemAssignList) {
		List<String> resourceIdList = new ArrayList<String>();
		for (RoleItemAssign assign : roleItemAssignList) {
			resourceIdList.add(String.valueOf(assign.getRoleItemId()));
		}
		return resourceIdList;
	}

	private List<RoleItemAssign> getAllRoleResourceListByUserId(Integer userId) {
		List<RoleItemAssign> roleItemAssignList = new ArrayList<RoleItemAssign>();
		List<UserRoleAssign> list = userRoleAssignDao.queryByUserId(userId);
		if (list != null) {
			for (UserRoleAssign assign : list) {
				roleItemAssignList.addAll(roleItemAssignDao
						.queryByRoleId(assign.getRoleId()));
			}
		}
		return roleItemAssignList;
	}

	public void userRoleUnassign(UserRoleAssign o) {
		userRoleAssignDao.remove(o);
	}
	/**
	 * 根据用户取得角色列表
	 * @param userid
	 * @return
	 */
	public List<UserRoleAssign> queryRolesByUserId(Integer userid){
		return userRoleAssignDao.queryByUserId(userid);
	}
	/**
	 * 校验角色名是否重复
	 * @param roleName
	 * @param roleId
	 * @return 
	* @create  2010-11-4 下午03:39:23 chenzy
	* @history
	 */
	public boolean isRoleNameExist(String roleName,Integer roleId){
		List<Role>  list = getAll();
		for(int i=0;i<list.size();i++){
			Role role = list.get(i);
			if(role.getRoleName().equals(roleName)&&roleId.intValue()!=role.getId().intValue()){
				return true;
			}
		}
		return false;
	}
}
