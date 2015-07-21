package com.dolphin.domain;

import com.core.BaseEntity;


/**
 * 用户角色分配Entity.
 *
 * @author: wanglf
 * @since: Feb 18, 2008 10:11:48 AM
 */
public class UserRoleAssign extends BaseEntity {
	private static final long serialVersionUID = 4035369819999620676L;

	private Integer roleId;

    private Integer userId;
    
    private Integer proxyUserId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public Integer getProxyUserId() {
		return proxyUserId;
	}

	public void setProxyUserId(Integer proxyUserId) {
		this.proxyUserId = proxyUserId;
	}
    
}