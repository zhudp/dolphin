package com.dolphin.web.dolphin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.dolphin.domain.UserSecurity;
import com.dolphin.domain.resource.Resource;
import com.dolphin.service.ResourceService;
import com.dolphin.service.RoleService;
import com.dolphin.service.UserService;
import com.hundsun.ctim.service.sysupdate.SysUpdateLogService;
import com.opensymphony.xwork2.ActionContext;

/*******************************************************************************
 * Index控制类.
 * 
 * @author: yanghb
 * @since: 2009-5-13 上午10:47:22
 * @history: ***********************************************
 * @file: IndexAction.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
// @Component
// @Scope("prototype")
@SuppressWarnings("serial")
// @Results( { @Result(name = StrutsAction.RELOAD, location = "main.action") })
public class MainAction extends StrutsAction {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private SysUpdateLogService sysUpdateLogService;

	/**
	 * 获取Index信息：包括功能菜单等.
	 */
	public String index() throws Exception {
		//系统维护公告
		this.getRequest().setAttribute("updateLogStr", sysUpdateLogService.getUpdateLogStr());
		User user = RemoteUser.get();
		// 功能菜单
		List<Resource> menusOfUser = userService.getMenusByUser(user);
		ActionContext.getContext().put("treeMenu",
				resourceService.getTreeFromMenusJS(menusOfUser));
		ActionContext.getContext().put("user", user);
		ActionContext.getContext().put("mainResource",
				JsonUtils.bean2JsonArray(resourceService.getAllResourceId()));
		ActionContext.getContext().put(
				"userResource",
				JsonUtils.bean2JsonArray(roleService
						.getUserOwnResourceIdOrderList(user.getId())));
		
		// 用户密码为初始密码时，提示修改密码
		if(UserSecurity.getMD5Pwd(UserSecurity.ACCOUNT_PWD_INIT,null).equals(user.getPassword())) {
			this.getRequest().setAttribute("isInitPwd", "true");
		}else {
			this.getRequest().setAttribute("isInitPwd", "false");
		}
		return "index";
	}
	
	/**
	 * Index框架页面跳转.
	 */
	@SuppressWarnings("unchecked")
	public String frameTransfer() throws Exception {
		
		HttpServletRequest req = this.getRequest();
		String navigateUrl = req.getParameter("navigateUrl");
		
		Map parmMap = this.getRequest().getParameterMap();
		StringBuffer strParam = new StringBuffer();
		
		Iterator it = parmMap.entrySet().iterator();
		while (it.hasNext()) {    
	        Map.Entry pairs = (Map.Entry)it.next();
	        if(!"navigateUrl".equals(pairs.getKey().toString())) {
	        	String value = req.getParameter(pairs.getKey().toString());
	        	strParam.append(pairs.getKey()).append("=").append(value).append("&");
	        }
		}   
		navigateUrl += "?"+strParam.toString();
		navigateUrl = navigateUrl.substring(0, navigateUrl.length()-1);
		//System.out.println("main.frameTransfer跳转===="+navigateUrl);
		this.getRequest().setAttribute("navigateUrl", navigateUrl);
		return "frameTransfer";
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
