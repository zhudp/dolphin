package com.hundsun.ctim.service.custom;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.custom.CustomDao;
import com.hundsun.ctim.dao.custom.CustomVisitDao;
import com.hundsun.ctim.domain.custom.Custom;
import com.hundsun.ctim.domain.custom.CustomVisit;

@Service
public class CustomServiceImp extends BaseService {
	
    @Autowired
    private CustomDao customDao;
    @Autowired
    private CustomVisitDao customVisitDao;
    
    /**
     * 根据ID获取客户信息
     * @param id
     * @return
     */
	public Custom getById(Long id) {
		return customDao.get(id);
	}
	
	/**
	 * 列表查询
	 */
	public List<Custom> query(Map<String, Object> paramsMap) {
		return customDao.query(paramsMap);
	}
	
	/**
	 * 分页查询
	 */
	public Page queryPaged(Map<String, String> paramsMap){
		return customDao.queryPaged(paramsMap);
	}
	
	
    /**
     * 新增客户信息
     * @return
     */
	public void insert(Custom custom) {
		customDao.insert(custom);
	}
	
	/**
	 * 更新客户信息
	 * @return
	 */
	public void update(Custom custom) {
		customDao.update(custom);
	}
	/**
	 * 更新客户信息
	 * @return
	 */
	public void updateSelective(Custom custom) {
		customDao.updateSelective(custom);
	}
	
	/**
	 * 根据ID删除
	 */
	public void removeById(Long customId) {
		customDao.removeById(customId);
	}
	
	
	///////////-------------客户拜访记录-----------///////////////
	/**
	 * 分页查询拜访记录
	 */
	public Page queryPagedVisit(Map<String, String> paramsMap){
		return customVisitDao.queryPaged(paramsMap);
	}
	
    /**
     * 根据ID获取客户信息
     * @param id
     * @return
     */
	public CustomVisit getVisitById(Long id) {
		return customVisitDao.get(id);
	}
	
    /**
     * 新增客户拜访信息
     * @return
     */
	public void insert(CustomVisit visit) {
		customVisitDao.insert(visit);
	}
	
	/**
	 * 新增客户拜访信息
	 * @return
	 */
	public void updateVisitSelective(CustomVisit visit) {
		customVisitDao.updateSelective(visit);
	}
	
	/**
	 * 根据ID删除客户拜访信息
	 */
	public void removeVisitById(Long visitId) {
		customVisitDao.removeById(visitId);
	}
	

}