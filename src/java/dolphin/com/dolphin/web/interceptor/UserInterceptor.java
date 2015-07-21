package com.dolphin.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.core.utils.WebUtils;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.dolphin.service.UserService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserService userService;

	@Override
	public String intercept(ActionInvocation action) throws Exception {
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		HttpServletRequest request =ServletActionContext.getRequest();
		//如果没有授权，跳转到登录页
		if(authentication==null){
			processResponse(ServletActionContext.getResponse(), request);
			return "noauthority";			
		}
		Object localUser = authentication.getPrincipal();
			
		if(localUser==null){
			ServletActionContext.getResponse().addHeader("sessionstatus", "timeout");
			return "timeout";
		}
		if (localUser instanceof UserDetails) {
			User userDetail = (User) localUser;
			RemoteUser.set(userDetail);
		} else { //匿名用户
			processResponse(ServletActionContext.getResponse(), request);
			return "noauthority";
		}
		String result = action.invoke();
		RemoteUser.remove();
		return result;
	}
	
	/**
	 * Session已过期或用户未登录下对response的处理
	 * @param response
	 * @param request 
	* @create  2010-11-30 下午04:47:21 chenzy
	* @history
	 */
	private void processResponse(HttpServletResponse response,HttpServletRequest request){
		if(request instanceof MultiPartRequestWrapper){
			response.setContentType("text/html;charset=UTF-8");
		}
		else {
			response.setContentType("text/x-json;charset=UTF-8");
		}
		//判断是否是Ajax请求
		String formOrGrid = request.getParameter("formOrGrid");
		if (StringUtils.isNotBlank(formOrGrid)){		
			PrintWriter out;
			try {
				String message ="会话 已过期，请重新登录！";
				if(formOrGrid.equals("form")){
					message=WebUtils.getExtFailureMessage(message);
				}
				out = response.getWriter();
				out.print(message);
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {   
			ServletActionContext.getResponse().addHeader("sessionstatus", "timeout");
		}
	}

}
