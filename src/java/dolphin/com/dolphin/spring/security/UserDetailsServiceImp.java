package com.dolphin.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dolphin.domain.User;
import com.dolphin.domain.UserRoleAssign;
import com.dolphin.domain.UserSecurity;
import com.dolphin.service.RoleService;
import com.dolphin.service.UserService;
import com.dolphin.sys.AppConstant;


public class UserDetailsServiceImp implements UserDetailsService {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(UserDetailsServiceImp.class);

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("loadUserByUsername(String) - start");
		}
		User user = userService.findUserByLoginName(username);
	
		if (user == null) {
			String message = "用户" + username + " 不存在";
			logger.error(message);
			throw new UsernameNotFoundException(message);
		}
		if (user.getStatus().equals(UserSecurity.STATUS_DEL)) {
			String message = "用户" + username + "已锁定，不能登录";
			logger.error(message);
			throw new UsernameNotFoundException(message);
		}
		Collection<GrantedAuthority>  auths =obtainGrantedAuthorities(user);
		user.setAuthorities(auths);
		if (logger.isDebugEnabled()) {
			logger.debug("loadUserByUsername(String) - end"); //$NON-NLS-1$  
		}
		return user;
	}
	
	/**
	 * 获得用户所有角色的权限.
	 */
	private Collection<GrantedAuthority> obtainGrantedAuthorities(User user) {
		Collection<GrantedAuthority> authSet = new ArrayList<GrantedAuthority>();
		// 构造登陆用户所有的权限操作项
		List<UserRoleAssign> roleList = roleService.queryRolesByUserId(user
				.getId());
		if (logger.isDebugEnabled()) {
			logger.debug(user.getId() + "用户权限：");
		}
		for (UserRoleAssign role : roleList) {
			if (logger.isDebugEnabled()) {
				logger.debug("\n A_" + role);
			}
			authSet.add(new GrantedAuthorityImpl("A_"+role.getRoleId().toString()));
		}
		return authSet;
	}
}
