package com.dolphin.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;



public class UrlAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		//System.out.println(exception.getMessage());
		request.setAttribute("errorLogin", "用户名或密码错误，请重新输入");
		
		super.onAuthenticationFailure(request, response, exception);
	}

}
