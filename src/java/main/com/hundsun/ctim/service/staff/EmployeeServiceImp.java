package com.hundsun.ctim.service.staff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.staff.EmployeeDao;
import com.hundsun.ctim.domain.staff.Employee;

@Service
public class EmployeeServiceImp extends BaseService {
	
    @Autowired
    private EmployeeDao employeeDao;
    
    /**
     * 根据ID获取员工信息
     * @param id
     * @return
     */
	public Employee getById(Long id) {
		return employeeDao.get(id);
	}
	
	/**
	 * 列表查询
	 */
	public List<Employee> query(Map<String, Object> paramsMap) {
		return employeeDao.query(paramsMap);
	}
	
	/**
	 * 分页查询
	 */
	public Page queryPaged(Map<String, String> paramsMap){
		return employeeDao.queryPaged(paramsMap);
	}
	
	
    /**
     * 新增员工信息
     * @return
     */
	public void insert(Employee employee) {
		employeeDao.insert(employee);
	}
	
	/**
	 * 更新员工信息
	 * @return
	 */
	public void update(Employee employee) {
		employeeDao.update(employee);
	}
	/**
	 * 更新员工信息
	 * @return
	 */
	public void updateSelective(Employee employee) {
		employeeDao.updateSelective(employee);
	}
	
	/**
	 * 根据ID删除
	 */
	public void removeById(Long employeeId) {
		employeeDao.removeById(employeeId);
	}
	
}