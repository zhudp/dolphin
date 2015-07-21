package com.dolphin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.dolphin.dao.SystemLogDao;
import com.dolphin.domain.SystemLog;

/**
 * ***********************************************
 * 
 * @file: SystemLogServiceImp.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 *             ***********************************************
 * @package: com.hs.common.service.implement
 * @class: SystemLogServiceImp
 * @description:
 * @author: lilq
 * @since: 2008-5-28-19:27:24
 * @history:
 */
@Service
public class SystemLogService extends BaseService {
	@Autowired
	private SystemLogDao entityDao;

	public SystemLog getSystemLog(Integer id) {
		Assert.notNull(id);
		return entityDao.get(id);
	}

	public Integer insertSystemLog(SystemLog o) {
		Assert.notNull(o);
		return (Integer) entityDao.insert(o);
	}

	public void updateSystemLog(SystemLog o) {
		Assert.notNull(o);
		entityDao.update(o);
	}

	public void deleteSystemLog(SystemLog o) {
		Assert.notNull(o);
		entityDao.remove(o);
	}

	public Page querySystemLog(Map<String, String> parameterObject) {
		Assert.notNull(parameterObject);
		return entityDao.queryPaged(parameterObject);
	}
	
	public void insertSystemLogBatch(List<SystemLog> bufferList) {
		entityDao.insertBatch(bufferList);
	}
}
