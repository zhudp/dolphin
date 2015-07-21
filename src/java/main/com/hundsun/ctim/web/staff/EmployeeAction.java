package com.hundsun.ctim.web.staff;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.DateUtils;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.hundsun.ctim.Params;
import com.hundsun.ctim.domain.staff.Employee;
import com.hundsun.ctim.service.staff.EmployeeServiceImp;

/**
 * 员工管理
 * 
 */
@SuppressWarnings("serial")
public class EmployeeAction extends StrutsAction {
	
	@Autowired
	private EmployeeServiceImp employeeService;
	
	/**
	 * 员工查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = employeeService.queryPaged(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 获取员工详细信息
	 */
	public void getEmployee() throws Exception{
		
		String employeeId = this.getRequest().getParameter("employeeId");
		Employee employee = employeeService.getById(Long.valueOf(employeeId));
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(employee)));
	}
	

	/**
	 * 保存员工信息
	 * @throws Exception
	 */
	public void saveEmployee() throws Exception{
		
		Employee employee = bindEntity(Employee.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		employee.setGmtModify(now);
		employee.setModifier(user.getUserName());
		employee.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(employee.getEmployeeId() == null){
			employee.setGmtCreate(now);
			employee.setCreator(user.getUserName());
			employee.setCreatorId(Long.valueOf(user.getId()));
			employeeService.insert(employee);
		}
		
		// 更新
		else{
			employeeService.updateSelective(employee);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除员工信息
	 */
	public void remove() throws Exception {
		String strEmployeeId = this.getRequest().getParameter("employeeId");
		Employee employee = employeeService.getById(Long.valueOf(strEmployeeId));
		if(employee == null) {
			printText(messageFailureWrap("该员工信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		employee.setGmtModify(now);
		employee.setModifier(user.getUserName());
		employee.setModifierId(Long.valueOf(user.getId()));
		// 删除
		employee.setIsDeleted(Params.STATUS_ONE);
		employeeService.update(employee);
		
		printText(messageSuccuseWrap());
	}
}
