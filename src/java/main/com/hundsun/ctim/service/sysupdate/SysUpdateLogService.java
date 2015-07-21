package com.hundsun.ctim.service.sysupdate;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.core.dao.support.Page;
import com.hundsun.ctim.domain.sysupdate.SysUpdateLog;



/** 
 * @author: guozq
 * @since: 2011-3-3  下午02:04:53
 * @history:
 ************************************************
 * @file: SysUpdateLogService.java
 * @Copyright: 2010 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************/
@Service
public interface SysUpdateLogService {
	
	public SysUpdateLog get(Long id);
	
	
	public void update(SysUpdateLog s);
	
	
	public void insert(SysUpdateLog s);
	
	public Page queryPaged(Map<String,String> map);
	
	public void delete(Long id);
	
	
	/** 
	 * 获取更新公告要显示的内容
	 * @return 
	* @create  2011-3-9 下午04:54:48 guozq
	* @history  
	*/
	public String getUpdateLogStr();
	
}
