package com.dolphin.web.dolphin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.dolphin.service.RoleService;
import com.dolphin.service.UserRoleService;

/**
 * 用户角色Action
 * 
 * @author Evan.Shen
 * 
 */
@SuppressWarnings("serial")
public class UserRoleAction extends StrutsAction {
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void userRoleSave() throws Exception {
		String roleIdsStr = getRequest().getParameter("idsStr");
		Integer userId = Integer.valueOf(getRequest().getParameter("userId"));
		userRoleService.userRoleSave(userId, roleIdsStr);
		printText(messageSuccuseWrap());
	}

	public void getUserRoles() throws Exception {
		Integer userId = Integer.valueOf(getRequest().getParameter("userId"));
		Integer[] roleIds = userRoleService.getUserRoles(userId);
		StringBuffer str = new StringBuffer();
		for (Integer roleId : roleIds) {
			str.append("," + roleId);
		}
		if (str.length() > 0) {
			str.delete(0, 1);
		}
		printText("{success:true,data:[" + str + "]}");
	}

	public void userProxyRoleSave() throws Exception {
		User user = RemoteUser.get();
		String roleIdsStr = getRequest().getParameter("idsStr");
		Integer userId = Integer.valueOf(getRequest().getParameter("userId"));
		try {
			userRoleService.userProxyRoleSave(userId, roleIdsStr, user.getId());
			printText(messageSuccuseWrap());
		} catch (Exception e) {
			printText(this.messageFailureWrap(e.toString()));
		}

	}

	public void getUserRoleList() throws Exception {
		User user = RemoteUser.get();
		Map<String, String> queryParameter = bindMap();
		queryParameter.put("userId", user.getId().toString());
		Page page = roleService.queryRole(queryParameter);
		printJson(JsonUtils.bean2Json(page));
	}

	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
}
