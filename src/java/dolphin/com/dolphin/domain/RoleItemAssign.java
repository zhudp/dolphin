package com.dolphin.domain;

import com.core.BaseEntity;


/**
 * 角色元素分配Entity.
 * 
 * @author: wanglf
 * @since: Feb 18, 2008 10:11:14 AM
 */
public class RoleItemAssign extends BaseEntity {
	private static final long serialVersionUID = 1700733382312320842L;

	private Integer roleId;

	private String roleItemId;

	public Integer getRoleId() {
		return roleId;
	}

	public RoleItemAssign() {
	}

	public RoleItemAssign(Integer roleId, String roleItemId) {
		this.roleId = roleId;
		this.roleItemId = roleItemId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleItemId() {
		return roleItemId;
	}

	public void setRoleItemId(String roleItemId) {
		this.roleItemId = roleItemId;
	}
}