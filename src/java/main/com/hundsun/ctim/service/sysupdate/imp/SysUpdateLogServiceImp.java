package com.hundsun.ctim.service.sysupdate.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.core.utils.DateUtils;
import com.hundsun.ctim.dao.sysupdate.SysUpdateLogDao;
import com.hundsun.ctim.domain.sysupdate.SysUpdateLog;
import com.hundsun.ctim.service.sysupdate.SysUpdateLogService;

@Service
@Transactional
public class SysUpdateLogServiceImp extends BaseService implements SysUpdateLogService {
    @Autowired
    private SysUpdateLogDao entityDao;

    public void setSysUpdateLogDao(SysUpdateLogDao entityDao) {
        this.entityDao = entityDao;
    }

	public void delete(Long id) {
		SysUpdateLog s = entityDao.get(id);
		entityDao.getSqlMapClientTemplate().delete("SysUpdateLog_delete", s);
	}

	public SysUpdateLog get(Long id) {
		return entityDao.get(id);
	}

	public void insert(SysUpdateLog s) {
		entityDao.insert(s);
	}

	public Page queryPaged(Map<String, String> map) {
		
		return entityDao.queryPaged(map);
	}

	public void update(SysUpdateLog s) {
		entityDao.update(s);
	}
	public String getUpdateLogStr() {
		Date d = DateUtils.getCurrentDate();
		String updateDate = DateUtils.toDateString(d, "yyyy-MM-dd");
		String updateTime = DateUtils.toDateString(d, "HH:mm");
		Map<String,String> map = new HashMap<String, String>();
		map.put("updateDate", updateDate);
		map.put("updateTime", updateTime);
		map.put("sort", "updateDate");
		
		List<SysUpdateLog> list = entityDao.query(map);
		if(list.size()==0) return ""; 
		else {
			SysUpdateLog s = list.get(0);
			return s.getUpdateDate()+"日"+s.getUpdateTime()+"分，"+s.getContext();
		}
	}


	
}