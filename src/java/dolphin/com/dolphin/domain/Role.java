package com.dolphin.domain;

import com.core.BaseEntity;
import com.dolphin.Constants;

/**
 * 功能角色Entity.
 * 
 * @author: wanglf
 * @since: Jan 16, 2008 1:51:02 PM
 */
public class Role extends BaseEntity {

	private static final long serialVersionUID = -5113287643333750407L;

	private Integer id;

	private String roleName;

	private String description;

	private String status;
	
	

	private String userAssined; // 用户已分配

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserAssined() {
		if (userAssined == null)
			userAssined = Constants.WHETHER_NO;

		return userAssined;
	}

	public void setUserAssined(String userAssined) {
		this.userAssined = userAssined;
	}
}
