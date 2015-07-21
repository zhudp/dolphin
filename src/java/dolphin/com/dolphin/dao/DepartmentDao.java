package com.dolphin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.core.dao.IBatisEntityDao;
import com.dolphin.domain.Department;

/**
 * 部门Dao IBatis实现
 * 
 * @author: zhangyw
 * @since: 2008-5-20
 */
@Repository
@SuppressWarnings("unchecked")
public class DepartmentDao extends IBatisEntityDao<Department> {

	public List<Department> queryByParentId(Integer parentId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("parentId", parentId);

		return query(getEntityClass(), map);
	}

	public List<Department> getDepartmentList(HashMap<String, Integer> map) {
		return super.query(getEntityClass(), map);

	}

	public void departmentEdit(Department dept) {
		super.update(dept);
	}

	public Department queryByDepartmentId(Integer id) {
		return super.get(id);
	}

	/**
	 * 获取所有Department对象列表.
	 */
	@Override
	public List<Department> getAll() {
		return super.getAll();
	}
	public List<Department>getDepartmentListByUser(Integer userid){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("deptManager", userid);
		return query(getEntityClass(), map);
	}
	
	/**
	 * 获取 部门list
	 * @param map
	 * @return 
	 * @create  2012-1-11 上午10:32:56 jinrey
	 * @history
	 */
	public List<Department> getDeptList(Map<String, String> map) {
		return super.query(getEntityClass(), map);

	}
}
