package com.hundsun.ctim.web;

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
import com.hundsun.ctim.domain.custom.Custom;
import com.hundsun.ctim.domain.custom.CustomVisit;
import com.hundsun.ctim.service.custom.CustomServiceImp;

/**
 * 客户管理
 * 
 */
@SuppressWarnings("serial")
public class CustomAction extends StrutsAction {
	
	@Autowired
	private CustomServiceImp customService;
	
	/**
	 * 客户查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = customService.queryPaged(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 获取客户详细信息
	 */
	public void getCustom() throws Exception{
		
		String customId = this.getRequest().getParameter("customId");
		Custom custom = customService.getById(Long.valueOf(customId));
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(custom)));
	}
	

	/**
	 * 保存客户信息
	 * @throws Exception
	 */
	public void saveCustom() throws Exception{
		
		Custom custom = bindEntity(Custom.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		custom.setGmtModify(now);
		custom.setModifier(user.getUserName());
		custom.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(custom.getCustomId() == null){
			custom.setGmtCreate(now);
			custom.setCreator(user.getUserName());
			custom.setCreatorId(Long.valueOf(user.getId()));
			customService.insert(custom);
		}
		
		// 更新
		else{
			customService.updateSelective(custom);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除客户信息
	 */
	public void remove() throws Exception {
		String strCustomId = this.getRequest().getParameter("customId");
		Custom custom = customService.getById(Long.valueOf(strCustomId));
		if(custom == null) {
			printText(messageFailureWrap("该客户信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		custom.setGmtModify(now);
		custom.setModifier(user.getUserName());
		custom.setModifierId(Long.valueOf(user.getId()));
		// 删除
		custom.setIsDeleted(Params.STATUS_ONE);
		customService.update(custom);
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 查询客户拜访记录
	 */
	public void queryPagedVisit() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = customService.queryPagedVisit(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 获取客户拜访信息
	 */
	public void getCustomVisit() throws Exception{
		
		String visitId = this.getRequest().getParameter("visitId");
		CustomVisit visit = customService.getVisitById(Long.valueOf(visitId));
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(visit)));
	}
	
	/**
	 * 添加客户拜访记录
	 */
	public void saveCustomVisit() throws Exception {
		CustomVisit visit = bindEntity(CustomVisit.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		visit.setGmtModify(now);
		visit.setModifier(user.getUserName());
		visit.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(visit.getVisitId() == null){
			visit.setGmtCreate(now);
			visit.setCreator(user.getUserName());
			visit.setCreatorId(Long.valueOf(user.getId()));
			customService.insert(visit);
		}
		
		// 更新
		else{
			customService.updateVisitSelective(visit);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除客户拜访信息
	 */
	public void removeVisit() throws Exception {
		String strVisitId = this.getRequest().getParameter("visitId");
		
		// 删除
		customService.removeVisitById(Long.valueOf(strVisitId));
		
		printText(messageSuccuseWrap());
	}
}
