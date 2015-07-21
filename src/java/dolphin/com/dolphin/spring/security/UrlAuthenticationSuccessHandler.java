package com.dolphin.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.core.utils.DateUtils;
import com.core.utils.WebUtils;
import com.dolphin.dao.UserSecurityDao;
import com.dolphin.domain.User;
import com.dolphin.domain.UserSecurity;

public class UrlAuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private UserSecurityDao userSecurityDao;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		//UserSecurity us = userService.getUserSecurity(user.getId());
		User user = (User)authentication.getPrincipal();
		UserSecurity us = user.getUserSecurity();
		String ip = WebUtils.getIpAddrByRequest(request);
		us.setLastLoginIp(ip);
		us.setLastLoginTime(DateUtils.getCurrentDate());
		us.setLoginCount(us.getLoginCount()==null?1:us.getLoginCount()+1);
		userSecurityDao.updateSelective(us);
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
