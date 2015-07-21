package com.dolphin.web.dolphin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.Department;
import com.dolphin.service.DepartmentService;

/*******************************************************************************
 * @description:部门控制跳转类包括部门树的显示、部门的增加、修改、删除等相关操作。
 * @author: yanghb
 * @since: 2009-5-13 下午03:11:06
 * @history: ***********************************************
 * @file: DepartmentAction.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
@SuppressWarnings("serial")
public class DepartmentAction extends StrutsAction {
	@Autowired
	private DepartmentService entityService;

	/**
	 * @description: 部门列表树的json数据显示
	 * @history:
	 */
	public void departmentTreeList() throws Exception {
		String parentId = Department.rootNode.toString();
		String curNode = new Integer(this.getRequest().getParameter(
				"node")).intValue() != -1 ? ((String) this.getRequest()
				.getParameter("node")) : parentId;
		printJson(entityService.assemblyCurSubDeptTree(new Integer(curNode)));
	}
	/**
	 * 将部门数一次性返回客户端
	 * @throws Exception
	 */
	public void buildWholeDeptTree() throws Exception{
		printJson(entityService.assemblySubDeptTree(Department.rootNode));
	}
	
	

	/**
	 * 将普通的部门树中的组织机构提出来
	 * 
	 * @param deptTree
	 * @return
	 * @create 2009-6-24 上午09:07:19 yanghb
	 * @history
	 */
	private String cutDatadictTree(String deptTree) {
		if (deptTree == null)
			return "";
		String targetFilterString = "\"deptType\" : \"organ\",";
		String splitString = "}";
		String[] items = deptTree.split(splitString);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < items.length; i++) {
			int index = items[i].indexOf(targetFilterString);
			if (index > -1) {
				sb.append(items[i]);
				sb.append(splitString);
			}
		}
		sb.append("]");
		String result = sb.toString();
		if (result.startsWith(",")) {
			result = result.substring(1);
		}
		if (!result.startsWith("[")) {
			result = "[" + result;
		}
		result = result.replaceAll(targetFilterString, "");
		return result;
	}

	/**
	 * @description: 部门列表树的json数据显示
	 * @history:
	 */
	public void orgTreeList() throws Exception {
		String parentId = Department.rootNode.toString();
		String curNode = new Integer(this.getRequest().getParameter(
				"node")).intValue() != -1 ? ((String) this.getRequest()
				.getParameter("node")) : parentId;
		String deptTree = entityService.assemblyCurSubDeptTree(new Integer(
				curNode));
		printJson(cutDatadictTree(deptTree));
	}

	/**
	 * 
	 * @description:部门列表树的json数据显示(用于Combox数据的显示。
	 * @history:
	 */
	public void comBoxTreeList() throws Exception {
		Integer curNode = new Integer(this.getRequest().getParameter("curNode"));
		printJson(entityService.assemblyParentDeptTree(curNode));
	}

	/**
	 * 
	 * @description:查询当前Department记录.
	 * @history:
	 */
	public void queryCurDepartment() throws Exception {
		Department dept = new Department();
		// 添加一个部门时候点击该增加的部门的节点时候前台会传一个"id"为-1 数值

		String addOperaotorNode = "-1";
		if (!addOperaotorNode.equals(this.getRequest().getParameter("id"))) {
			dept.setId(new Integer(this.getRequest().getParameter("id")));
			printJson(messageSuccuseWrap(JsonUtils.bean2Json(entityService
					.getDepartmentId(dept.getId()))));
		}
	}

	/**
	 * s
	 * 
	 * @description:查询当前Department名称信息.
	 * @history:
	 */
	public void get() throws Exception {
		Department dept = new Department();
		dept.setId(new Integer(this.getRequest().getParameter("id")));

		dept = entityService.getDepartmentId(dept.getId());
		StringBuffer str = new StringBuffer("{\"totalCount\": 1,\"result\":[");
		str.append("{ \"id\" : ");
		str.append("\"" + dept.getId() + "\",");
		str.append(" \"text\" : ");
		str.append(" \"" + dept.getDeptName() + "\"");
		str.append("},");
		str.delete(str.length() - 1, str.length());
		str.append("]}");
		printJson(str.toString());

	}

	/**
	 * 
	 * @description:保存单条Department记录.
	 * @history:
	 */
	@Override
	public String save() throws Exception {
		Department dept = bindEntity(Department.class);
		// 添加一个部门时候点击该增加的部门的节点时候前台会传一个"id"为-1 数值

		String addOperaotorNode = "-1";
		if (addOperaotorNode.equals(this.getRequest().getParameter("id"))) {
			entityService.insertDepartment(dept);
		} else {
			entityService.updateDepartment(dept);
		}
		printText(messageSuccuseWrap());
		return null;
	}

	/**
	 * 
	 * @description:删除Department记录.
	 * @history:
	 */
	public void delete() throws Exception {
		Department o = bindEntity(Department.class);
		String message = entityService.deleteDepartment(o);
		if (!"".equals(message))
			printJson(messageFailureWrap(message));
		else
			printJson(messageSuccuseWrap());

	}

	/**
	 * 
	 * @description:通过当前的用户（部门经理）找到该用户（部门经理）所管辖的部门.
	 * @history:
	 */

	public void getDeptListByUser() throws Exception {
		// Department o = bindEntity(Department.class);
		// TODO 待遗弃
		List<Department> deptList = entityService.getAll();
		printJson(JsonUtils.bean2Json(new Page(deptList.size(), deptList)));
	}

	public void setDepartmentService(DepartmentService entityService) {
		this.entityService = entityService;
	}

}
