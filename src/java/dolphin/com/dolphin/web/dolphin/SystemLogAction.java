/**
 * 系统日志查询Action
 */
package com.dolphin.web.dolphin;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.SystemLog;
import com.dolphin.domain.User;
import com.dolphin.service.DatadictService;
import com.dolphin.service.SystemLogService;
import com.hundsun.ctim.domain.sysupdate.SysUpdateLog;
import com.hundsun.ctim.service.sysupdate.SysUpdateLogService;

/**
 * ***********************************************
 * 
 * @file: SystemLogAction.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 *             ***********************************************
 * @package: com.hs.common.web
 * @class: SystemLogAction
 * @description:
 * @author: lilq
 * @since: 2008-5-28-20:23:25
 * @history:
 */
@SuppressWarnings("serial")
public class SystemLogAction extends StrutsAction {
	@Autowired
	private SystemLogService entityService;
	@Autowired
	private DatadictService datadictService;
	@Autowired
	private SysUpdateLogService sysUpdateLogService;

	/**
	 * 根据id获取单条SystemLog记录.
	 */
	public void get() throws Exception {
		SystemLog o = entityService.getSystemLog(Integer.valueOf(this
				.getRequest().getParameter("id")));
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(o)));
	}

	/**
	 * 分页查询SystemLog列表.
	 */
	public void queryPaged() throws Exception {
		Map<String, String> queryParameter = bindMap();
		Page page = entityService.querySystemLog(queryParameter);
		printJson(JsonUtils.bean2Json(page));
	}

	/**
	 * 保存单条SystemLog记录.
	 */
	@Override
	public String save() throws Exception {
		SystemLog o = bindEntity(SystemLog.class);
		if (o.getSlogId() == null)
			entityService.insertSystemLog(o);
		else
			entityService.updateSystemLog(o);
		printText(messageSuccuseWrap());
		return null;
	}

	/**
	 * 删除SystemLog记录.
	 */
	public String delete() throws Exception {
		SystemLog o = bindEntity(SystemLog.class);
		entityService.deleteSystemLog(o);
		printText(messageSuccuseWrap());
		return null;
	}

	public void setSystemLogService(SystemLogService entityService) {
		this.entityService = entityService;
	}

	public void queryOperType() throws Exception {
		printJson(JsonUtils.getJsonCombox(datadictService
				.getDatadictMap("OPER_TYPE")));
	}
	
	public String saveUpdateLog()throws Exception {
		User user = RemoteUser.get();
		SysUpdateLog o = bindEntity(SysUpdateLog.class);
		o.setCreator(user.getAccount());
		o.setGmtCreate(new Date());
		if (o.getId() == null)
			sysUpdateLogService.insert(o);
		else
			sysUpdateLogService.update(o);
		printText(messageSuccuseWrap());
		return null;
	}
	public void getUpdateLog()throws Exception {
		SysUpdateLog o = sysUpdateLogService.get(Long.valueOf(this.getRequest().getParameter("id")));
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(o)));
	}
	public void queryUpdateLog()throws Exception {
		Map<String, String> queryParameter = bindMap();
		Page page = sysUpdateLogService.queryPaged(queryParameter);
		printJson(JsonUtils.bean2Json(page));
	}
	public String deleteUpdateLog()throws Exception {
		sysUpdateLogService.delete(Long.valueOf(this
				.getRequest().getParameter("id")));
		printText(messageSuccuseWrap());
		return null;
	}
}
