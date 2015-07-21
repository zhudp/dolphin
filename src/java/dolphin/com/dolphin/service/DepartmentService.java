package com.dolphin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.dolphin.dao.DepartmentDao;
import com.dolphin.dao.UserDao;
import com.dolphin.domain.Department;
import com.dolphin.domain.User;

/*******************************************************************************
 * @description:部门业务控制接口实现类包括部门树的显示、部门的增加、修改、删除等相关操作。

 * @author: zhangyw
 * @since: Jun 26, 2008 8:38:58 PM
 * @history: ***********************************************
 * @file: DepartmentService.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
@Service
public class DepartmentService extends BaseService {
	@Autowired
	private DepartmentDao entityDao;
	@Autowired
	private UserDao userDao;

	public void setDepartmentDao(DepartmentDao entityDao) {
		this.entityDao = entityDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void insertDepartment(Department o) {
		try {
			if (o.getParentId() == 0) {
				o.setParentId(Department.rootNode);
				// o.setParentName(o.getDeptName());
				entityDao.insert(o);
			} else {
				Department dept = this.getDepartmentId(o.getParentId());
				dept.setIsLeaf("n");
				entityDao.update(dept);
				String idPath = dept.getFullIdPath() == null ? "" : dept
						.getFullIdPath();
				String namePath = dept.getFullNamePath() == null ? "" : dept
						.getFullNamePath();
				// id全路径格式：/1/2/
				o.setFullIdPath(idPath + dept.getId() + "/");
				o.setFullNamePath(namePath + dept.getDeptName() + "/");
				o.setOrgLever(Integer
						.valueOf((dept.getOrgLever().intValue() + 1)));

				// o.setParentName(dept.getDeptName());
				entityDao.insert(o);
			}
		} finally {

		}
	}

	public void updateDepartment(Department o) {
		entityDao.update(o);
	}

	public String deleteDepartment(Department o) {
		String message = "";
		if (this.getCurSubDeptUserList(o.getId()).size() > 0) {
			message = "该部门下有用户不能删除该部门！";
		} else if (this.getSubDepartmentList(o.getId()).size() > 0) {
			message = "该部门下有子部门,不能删除该部门！";
		} else {
			entityDao.remove(o);
		}

		return message;
	}

	public Department getDepartmentId(Integer id) {
		Department dept = entityDao.get(id);
		// if (dept.getDeptManager() != null) {
		// User user = userDao.queryByUserId(dept.getDeptManager());
		// dept.setDeptManagerName(user.getUserName());
		// }
		return dept;
	}

	public List<Department> getAll() {
		return entityDao.getAll();
	}

	public List<Department> getCurSubDepartmentList(Integer id) {
		HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
		nameMap.put("parentId", id);
		return entityDao.getDepartmentList(nameMap);
	}

	public List<Department> getSubDepartmentList(Integer id) {
		List<Integer> idList = new ArrayList<Integer>();
		List<Department> deptList = new ArrayList<Department>();
		List<Department> list = getCurSubDepartmentList(id);
		StringTokenizer st = new StringTokenizer(getSubDeptDepartment(list),
				"id:");
		while (st.hasMoreTokens()) {
			String deptId = st.nextToken();
			idList.add(new Integer(deptId));
		}
		for (Integer tempId : idList) {
			deptList.add(entityDao.queryByDepartmentId(tempId));
		}

		return deptList;
	}

	private String getSubDeptDepartment(List<Department> subDeptList) {
		StringBuffer strBuf = new StringBuffer();
		for (Department deparment : subDeptList) {
			strBuf.append("id:" + deparment.getId());
			List<Department> list = getCurSubDepartmentList(deparment.getId());
			if (list != null && list.size() > 0) {
				strBuf.append(getSubDeptDepartment(list));
			}
		}
		return strBuf.toString();
	}

	public String assemblySubDeptTree(Integer id) {
		List<Department> subList = getCurSubDepartmentList(id);
		StringBuffer treeStr = new StringBuffer("[");
		treeStr.append(assemblySubDeptTree(subList));
		treeStr.append("]");
		return treeStr.toString();

	}

	public String assemblyCurSubDeptTree(Integer id) {
		List<Department> subList = getCurSubDepartmentList(id);
		StringBuffer treeStr = new StringBuffer("[");
		treeStr.append(assemblyCurSubDeptTree(subList));
		treeStr.append("]");
		return treeStr.toString();

	}

	public String assemblyParentDeptTree(Integer id) {

		List<Department> subList = this.getParentDeptList(id);
		StringBuffer treeStr = new StringBuffer("[");
		treeStr.append(assemblyParentDeptTree(subList));
		treeStr.append("]");
		return treeStr.toString();
	}

	private String assemblyParentDeptTree(List<Department> subDeptList) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = subDeptList.size() - 1; i >= 0; i--) {
			Department department = subDeptList.get(i);
			if (i > 0) {
				if (i == subDeptList.size() - 1) {
					strBuf.append("{\"text\" : \"" + department.getDeptName()
							+ "\",");
					strBuf.append("\"id\" : \"" + department.getId() + "\",");
					strBuf.append("\"deptType\" : \""
							+ department.getDeptType() + "\",");
					strBuf.append("\"leaf\" : false ,");
					strBuf.append("\"cls\" :\"fold\",");
					strBuf.append("\"children\" :[");
				} else {
					strBuf.append("{\"text\" : \"" + department.getDeptName()
							+ "\",");
					strBuf.append("\"id\" : \"" + department.getId() + "\",");
					strBuf.append("\"deptType\" : \""
							+ department.getDeptType() + "\",");
					strBuf.append("\"leaf\" : false ,");
					strBuf.append("\"cls\" :\"fold\",");
					strBuf.append("\"children\" :[");
				}
			} else {
				strBuf.append("{\"text\" : \"" + department.getDeptName()
						+ "\",");
				strBuf.append("\"id\" : \"" + department.getId() + "\",");
				strBuf.append("\"deptType\" : \"" + department.getDeptType()
						+ "\",");
				strBuf.append("\"cls\" :'file',");
				strBuf.append("\"leaf\" : true");
				for (int j = subDeptList.size() - 1; j >= 0; j--) {
					strBuf.append("}");
					strBuf.append("]");
				}
			}
		}
		if (strBuf.length() > 0) {
			strBuf.delete(strBuf.length() - 1, strBuf.length());
		}
		return strBuf.toString();
	}

	private String assemblySubDeptTree(List<Department> subDeptList) {
		StringBuffer strBuf = new StringBuffer();
		for (Department department : subDeptList) {
			strBuf.append("{\"text\" : \"" + department.getDeptName() + "\",");
			strBuf.append("\"id\" : \"" + department.getId() + "\",");
			strBuf.append("\"deptType\" : \"" + department.getDeptType()
					+ "\",");
			HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
			nameMap.put("parentId", department.getId());
			List<Department> list = entityDao.getDepartmentList(nameMap);
			if (list != null && list.size() > 0) {
				strBuf.append("\"leaf\" : false ,");
				strBuf.append("\"cls\" :\"fold\",");
				strBuf.append("\"children\" :[");
				strBuf.append(assemblySubDeptTree(list));
				strBuf.append("]");
			} else {
				strBuf.append("\"cls\" :'file',");
				strBuf.append("\"leaf\" : true");
			}
			strBuf.append("},");
		}

		if (strBuf.length() > 0) {
			strBuf.delete(strBuf.length() - 1, strBuf.length());
		}

		return strBuf.toString();
	}

	private String assemblyCurSubDeptTree(List<Department> subDeptList) {
		StringBuffer strBuf = new StringBuffer();
		for (Department department : subDeptList) {
			strBuf.append("{\"text\" : \"" + department.getDeptName() + "\",");
			strBuf.append("\"id\" : \"" + department.getId() + "\",");
			strBuf.append("\"deptType\" : \"" + department.getDeptType()
					+ "\",");
			HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
			nameMap.put("parentId", department.getId());
			List<Department> list = entityDao.getDepartmentList(nameMap);
			if (list != null && list.size() > 0) {
				strBuf.append("\"leaf\" : false ,");
				strBuf.append("\"cls\" :\"fold\"");
			} else {
				strBuf.append("\"cls\" :'file',");
				strBuf.append("\"leaf\" : true");
			}
			strBuf.append("},");
		}

		if (strBuf.length() > 0) {
			strBuf.delete(strBuf.length() - 1, strBuf.length());
		}

		return strBuf.toString();
	}

	public String assemblyCurParentDeptTree(Integer id) {
		Department dept = entityDao.get(id);
		dept = entityDao.get(dept.getParentId());
		return assemblyCurSubDeptTree(dept.getId());

	}

	@SuppressWarnings("unchecked")
	public List<Department> getParentDeptList(Integer id) {
		List tempList = new ArrayList();
		while (true) {
			// id 为 顶级部门 （父节点）为0
			if (id.intValue() == Department.rootNode.intValue()) {
				break;
			}
			Department dept = entityDao.queryByDepartmentId(id);
			tempList.add(dept);
			if (dept.getParentId().intValue() != Department.rootNode.intValue()) {
				dept = entityDao.queryByDepartmentId(dept.getParentId());
				tempList.add(dept);
			}
			id = dept.getParentId();
		}
		return tempList;
	}

	public List<User> getParentDeptUserList(Integer id) {
		List<User> tempList = new ArrayList<User>();
		List<Department> list = getParentDeptList(id);
		for (Department dept : list) {
			// TODO 暂时注释，部门中无负责人
			// User user = userDao.queryByUserId(dept.getDeptManager());
			// tempList.add(user);
		}
		return tempList;
	}

	public List<User> getCurSubDeptUserList(Integer id) {
		HashMap<String, String> nameMap = new HashMap<String, String>();
		nameMap.put("deptId", id.toString());
		return userDao.getUserList(nameMap);

	}

	public List<User> getSubDeptUserList(Integer id) {
		List<User> userList = new ArrayList<User>();
		List<Department> list = getSubDepartmentList(id);
		for (Department dept : list) {
			HashMap<String, String> nameMap = new HashMap<String, String>();
			nameMap.put("deptId", dept.getId().toString());
			List<User> userTempList = userDao.getUserList(nameMap);
			for (User tempUser : userTempList) {
				userList.add(tempUser);
			}
		}
		return userList;
	}

	private boolean hasMoreDepartment(Integer oldManager) {
		HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
		nameMap.put("deptManager", oldManager);
		List<Department> list = entityDao.getDepartmentList(nameMap);
		if (list.size() > 1)
			return true;
		else
			return false;

	}

	public String assimblyDepartName(Integer id) {
		int i = 1;
		StringBuffer sb = new StringBuffer();
		List<Department> list = getParentDeptList(id);
		for (Department department : list) {
			sb.append(department.getId());
			if (i < list.size())
				sb.append(",");
			i = i + 1;
		}
		return sb.toString();
	}

	public List<Department> getDepartmentListByUser(Integer userId) {
		return entityDao.getDepartmentListByUser(userId);
	}
	
	/**
	 * 一次访问数据库 获取 ext 树
	 * @param nodeId
	 * @return 
	 * @create  2012-1-11 上午10:06:05 jinrey
	 * @history
	 */
	public String getDepartmentByNodeId(String deptType) {   
		// 执行查询，取得所有记录   
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("deptType", deptType);
		List<Department> list = entityDao.getDeptList(paramMap);
		// map
		Map<Integer, Department> map = new HashMap<Integer, Department>();
	    
		Department root = null; // 根结点 
		
		// 第一次循环，将结果集放到一个"Map键-值对"结构中，方便取出指定元素   
		for(Department treeNode : list) {    
			map.put(treeNode.getId(), treeNode);
			if(treeNode.getParentId() == 1){
	            root = treeNode;
	        }
		}
	       
	    // 第二次循环所有结点，将每个结点的子结点存入一个TreeSet中。*/
	    for(Department treeNode : list){
	        if(treeNode.getParentId() != 1){
	            map.get(treeNode.getParentId()).addChild(treeNode);
	        }
	    }
	  
	    // 递归方法toJsonString() 生成树字符串   
	    String treeStr = "[" + root.toJsonString() + "]";       
	    return treeStr;
	}
	
	public String getDepartmentById(String deptId){
		Department department = this.entityDao.get(Long.valueOf(deptId));
		String deptName = department.getDeptName();
		return deptName;
	}
}
