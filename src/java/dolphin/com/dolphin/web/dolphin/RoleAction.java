package com.dolphin.web.dolphin;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.Constants;
import com.dolphin.domain.Role;
import com.dolphin.domain.RoleItemAssign;
import com.dolphin.domain.UserRoleAssign;
import com.dolphin.service.ResourceService;
import com.dolphin.service.RoleService;

/**
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class RoleAction extends StrutsAction {
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;

	/**
	 * 获取Role记录生成前台下拉框.
	 */
	public void combo() throws Exception {
		List<Role> o = roleService.getAll();
		Role empty = new Role();
		empty.setRoleName(Constants.COMBO_EMPTY_LABEL);
		o.add(0, empty);
		printJson(JsonUtils.bean2Json(new Page(o.size(), o)));
	}

	/**
	 * 根据id获取单条Role记录.
	 */
	public void get() throws Exception {
		Role o = roleService.getRole(Integer.valueOf(this.getRequest()
				.getParameter("id")));
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(o)));
	}

	/**
	 * 分页查询Role列表.
	 */
	public void queryPaged() throws Exception {
		Map<String, String> queryParameter = bindMap();
		Page page = roleService.queryRole(queryParameter);
		printJson(JsonUtils.bean2Json(page));
	}

	public void getAll() throws Exception {
		List<Role> roles = roleService.getAll();
		Page page = new Page();
		page.setData(roles);
		printJson(JsonUtils.bean2Json(page));
	}

	/**
	 * 保存单条Role记录.
	 */
	@Override
	public String save() throws Exception {
		Role o = bindEntity(Role.class);
		if (o.getId() == null)
			roleService.insertRole(o);
		else
			roleService.updateRole(o);
		printText(messageSuccuseWrap());
		return null;
	}

	/**
	 * 删除Role记录.
	 */
	public void delete() throws Exception {
		Role o = bindEntity(Role.class);
		roleService.deleteRole(o);
		printText(messageSuccuseWrap());
	}

	/**
	 * 获取全部权限
	 * 
	 * @throws Exception
	 */
	public void getAllItems() throws Exception {
		String json = resourceService.getAllResourcesJason();
		printJson("{success:true,data:" + json + "}");

	}

	/**
	 * 获取一个角色的权限
	 * 
	 * @throws Exception
	 */
	public void getRoleItems() throws Exception {
		Integer roleId = Integer.valueOf(getRequest().getParameter("roleId"));
		List<RoleItemAssign> list = roleService.getRoleItems(roleId);

		StringBuffer str = new StringBuffer();
		for (RoleItemAssign roleItemAssign : list) {
			str.append("','" + roleItemAssign.getRoleItemId());
		}
		int length = str.length();
		if (length > 0) {
			str.delete(0, 2);
			str.append("'");
		}
		printText("{success:true,data:[" + str + "]}");
	}

	/**
	 * 保存一个角色的权限
	 * 
	 * @throws Exception
	 */
	public void saveRoleItems() throws Exception {
		Integer roleId = Integer.valueOf(getRequest().getParameter("roleId"));
		String[] itemIds = getRequest().getParameterValues("items");

		roleService.saveRoleItemsAssigning(roleId, itemIds);
		printText(messageSuccuseWrap());
	}

	/**
	 * 权限分配
	 */
	public void roleResourceAssignEdit() throws Exception {
		Integer roleId = Integer.valueOf(this.getRequest().getParameter(
				"roleId"));
		printJson(roleService.getMainResourceByRoleIdWithChecked(roleId));
	}

	/**
	 * 权限分配-保存.
	 */
	public void roleItemAssignSave() throws Exception {
		Integer roleId = Integer.valueOf(this.getRequest().getParameter(
				"roleId"));
		String trees = this.getRequest().getParameter("trees");
		roleService.saveRoleItemsAssigning(roleId, trees.split(","));

		printText(messageSuccuseWrap());
	}

	/**
	 * 用户角色分配-分页查询Role列表，并根据userId展现是否已经分配.
	 */
	public void userRoleAssignQueryPaged() throws Exception {
		String userIdStr = this.getRequest().getParameter("userId");
		if (StringUtils.isBlank(userIdStr)) {
			printJson(JsonUtils.bean2Json(new Page()));
			return;
		}

		Integer userId = Integer.valueOf(userIdStr);
		Map<String, String> queryParameter = bindMap();

		Page page = roleService
				.userRoleAssignQueryPaged(queryParameter, userId);
		printJson(JsonUtils.bean2Json(page));
	}

	/**
	 * 用户角色分配-分配角色给所选用户.
	 */
	public void userRoleAssign() throws Exception {
		UserRoleAssign o = bindEntity(UserRoleAssign.class);
		roleService.userRoleAssign(o);
		printText(messageSuccuseWrap());
	}

	/**
	 * 用户角色分配-取消分配角色给所选用户.
	 */
	public void userRoleUnassign() throws Exception {
		UserRoleAssign o = bindEntity(UserRoleAssign.class);
		roleService.userRoleUnassign(o);
		printText(messageSuccuseWrap());
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
}
