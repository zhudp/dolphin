package com.dolphin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.dolphin.domain.resource.Resource;

/***********************************************************************************************************
 * 权限管理Service实现类
 * 
 * 
 * @author: chennp
 * @since: 2008-7-4 下午03:24:23
 * @history: ***********************************************
 * @file: PermissionManagerImp.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 **********************************************************************************************************/
@Service
public class PermissionManager {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleService roleService;
	/**
	 * 没有在控制范围内，默认给于是否通过
	 */
	@Autowired
	private Boolean defaultAccessable = Boolean.TRUE;

	public void setDefaultAccessable(Boolean defaultAccessable) {
		this.defaultAccessable = defaultAccessable;
	}

	public Boolean getDefaultAccessable() {
		if (null == defaultAccessable)
			return Boolean.FALSE;
		return defaultAccessable;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public boolean hasPermission(Integer userId, String resourceId) {
		Assert.notNull(resourceId, "resourceId is null");
		Assert.notNull(userId, "userId is null");
		List<String> userOwnResourceIdList = roleService
				.getUserOwnResourceIdOrderList(userId);
		return resourceService.hasOwnResource(userOwnResourceIdList,
				resourceService.get(resourceId));
	}

	public boolean hasPermissionByActionProxy(Integer userId,
			String proxyActionName) {
		Assert.notNull(proxyActionName, "proxyActionName is null");
		Assert.notNull(userId, "userId is null");
		if (!resourceService.isExsitResourceDefinition(proxyActionName)) {
			return getDefaultAccessable().booleanValue();
		}

		List<String> userResourceIdList = roleService
				.getUserOwnResourceIdOrderList(userId);
		List<Resource> resourceList = resourceService
				.queryByURI(proxyActionName);
		if (resourceList.size() > 0) {
			return resourceService.hasOwnResource(userResourceIdList,
					resourceList.get(0));
		}
		return false;
	}

}
