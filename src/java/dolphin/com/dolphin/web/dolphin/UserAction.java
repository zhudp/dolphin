package com.dolphin.web.dolphin;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.DateUtils;
import com.core.utils.JsonUtils;
import com.core.utils.StringUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.Department;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.dolphin.domain.UserSecurity;
import com.dolphin.service.DepartmentService;
import com.dolphin.service.UserService;
import com.hundsun.ctim.Params;

/*******************************************************************************
 * 用户控制类.
 * 
 * @author: yanghb
 * @since: 2009-5-13 下午05:38:11
 * @history: ***********************************************
 * @file: UserAction.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
@SuppressWarnings("serial")
public class UserAction extends StrutsAction {
	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 根据id获取单条User记录.
	 */
	public void get() throws Exception {
		User o = userService.getUser(Integer.valueOf(this.getRequest()
				.getParameter("id")));
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(o)));
	}

	/**
	 * 分页查询通过部门相关信息来查找部门经理
	 * 
	 */
	public void queryUserByDepartment() throws Exception {
		String rootNode = "-1";// 根节点

		Page page = null;
		Department dept = bindEntity(Department.class);
		Map<String, String> queryParameter = bindMap();
		if (this.getRequest().getParameter("deptId").equals("")
				|| this.getRequest().getParameter("deptId").equals(rootNode)) {
			queryParameter.put("deptId", null);
			queryParameter.put("deptIdList", departmentService
					.assimblyDepartName(dept.getParentId()));
			page = userService.queryPagedUser(queryParameter);
		} else {
			page = userService.queryPagedUser(queryParameter);
		}
		printJson(JsonUtils.bean2Json(page));
	}

	/**
	 * 分页查询User列表
	 */
	public void queryPaged() throws Exception {
		Map<String, String> queryParameter = bindMap();
		String rootNode = "-1";// 根节点
		if (rootNode.equals(this.getRequest().getParameter("deptId")))
			queryParameter.put("deptId", null);
		Page page = userService.queryPagedUser(queryParameter);
		List<User> userList = (List<User>) page.getData();
		
		printJson(JsonUtils.bean2Json(page,"yyyy-MM-dd HH:mm"));
	}

	/**
	 * 保存单条User记录.
	 */
	@Override
	public String save() throws Exception {
		
		HttpServletRequest request = this.getRequest();
		
		String[] reseauIds = request.getParameterValues("reseauId");
		String account = request.getParameter("account");
		
		User o = bindEntity(User.class);
		User user = RemoteUser.get();

		Date now = DateUtils.getCurrentDate();
		
		// 新增
		if (o.getId() == null) {
			o.setStatus(UserSecurity.STATUS_INIT);
			o.setCreator(user.getUserName());
			o.setGmtCreate(now);
			o.setModifier(user.getUserName());
			o.setGmtModify(now);
			userService.insertUser(o, account, reseauIds);
		}
		
		// 修改
		else {
			o.setModifier(user.getUserName());
			o.setGmtModify(now);
			userService.updateUser(o, account, reseauIds);
		}
		printText(messageSuccuseWrap());
		return null;
	}

	/**
	 * 删除User记录.
	 */
	public String delete() throws Exception {
		User o = bindEntity(User.class);
		if (Integer.valueOf(1).equals(o.getId())) {
			printText(messageFailureWrap("超级用户不允许删除!"));

		} else {
			userService.deleteUser(o);
			printText(messageSuccuseWrap());
		}
		return null;
	}

	/**
	 * 更改密码.
	 */
	public void changePassword() throws Exception {
		User user = RemoteUser.get();
		Integer userId = user.getId();
		String oldPassword = this.getRequest().getParameter("oldPassword");
		String newPassword = this.getRequest().getParameter("newPassword");
		String message = userService.changePassword(userId, oldPassword, newPassword);
		if ("".equals(message)) {
			printJson(messageSuccuseWrap());
		} else {
			printJson(messageFailureWrap(message));
		}
	}

	/**
	 * 初始化密码.
	 */
	public void initPassword() throws Exception {
		Integer userId = Integer.valueOf(this.getRequest().getParameter("userId"));
		userService.initPassword(userId);
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 根据所属社区查询用户信息
	 * @throws IOException 
	 */
	public void queryByArea() throws IOException {
		
		Map<String, String> queryParameter = bindMap();
		
		Page page = userService.queryPagedUser(queryParameter);
		List<User> userList = (List<User>) page.getData();
		
		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 设置居住证打印模板
	 * temp=?
	 */
	public void settemp() throws Exception {
		User user = RemoteUser.get();
		String temp = this.getRequest().getParameter("temp");
		
		if(!StringUtils.isBlank(temp)) {
			user.setZipCode(temp);
		}
		userService.update(user);
		printText(messageSuccuseWrap("居住证打印模板设置成功！"));
	}

}
