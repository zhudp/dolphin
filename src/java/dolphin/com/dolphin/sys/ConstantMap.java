package com.dolphin.sys;

import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class ConstantMap {
	/** 日志类型 */
	private TreeMap<String, String> slogType = new TreeMap<String, String>();

	/** 操作类型 */
	private TreeMap<String, String> operType = new TreeMap<String, String>();

	/** 数据角色类别 */
	private TreeMap<String, String> dataViewRoleType = new TreeMap<String, String>();

	/** 数据角色类别 */
	private TreeMap<String, String> dataViewRoleTypeSelect = new TreeMap<String, String>();

	/** 业务异常类别 */
	private TreeMap<String, String> businessException = new TreeMap<String, String>();

	private static ConstantMap constantMap = null;

	public static ConstantMap getInstance() {
		if (constantMap == null) {
			constantMap = new ConstantMap();
		}
		return constantMap;
	}

	public String getSlogTypeValue(String id) {
		if (StringUtils.isNotBlank(id))
			return slogType.get(id);
		return "";
	}

	public String getOperTypeValue(String id) {
		if (StringUtils.isNotBlank(id))
			return operType.get(id);
		return "";
	}

	public ConstantMap() {
		// 操作日志类型
		slogType.put(AppConstant.SYSTEM_LOG, "系统日志");
		slogType.put(AppConstant.SENSTIVE_LOG, "业务日志");
		//
		operType.put(AppConstant.OPER_TYPE_ADD, "新增");
		operType.put(AppConstant.OPER_TYPE_MODIFY, "修改");
		operType.put(AppConstant.OPER_TYPE_DELETE, "删除");
		// 数据角色类别
		dataViewRoleType.put(AppConstant.DATA_VIEW_ROLE_TYPE_SELF, "按市场楼层查看");
		dataViewRoleType.put(AppConstant.DATA_VIEW_ROLE_TYPE_DEPT, "按指定商位查看");
		dataViewRoleType.put(AppConstant.DATA_VIEW_ROLE_TYPE_ALL, "查看全部商位");
		dataViewRoleTypeSelect.put(AppConstant.DATA_VIEW_ROLE_TYPE_SELF,
				"按市场楼层查看");
		// 业务异常类别
		businessException.put(AppConstant.BUSINESSEXCEPTION_ACCOUNTISUSEING,
				"登录帐号已被使用，请重新设置.");
		businessException.put(AppConstant.BUSINESSEXCEPTION_NOACCOUNT,
				"登录帐号不存在，请更正后重新登录.");
		businessException.put(AppConstant.BUSINESSEXCEPTION_PASSWORDERROE,
				"{0}密码错误，{1}请更正后重新登录.");
		businessException.put(AppConstant.BUSINESSEXCEPTION_OLDPASSWORDERROE,
				"旧密码不正确，请更正后重新提交.");
		businessException.put(AppConstant.BUSINESSEXCEPTION_NEWPASSWORDERROE,
				"新密码应设置为大于六位的英文或数字.");
		businessException.put(AppConstant.BUSINESSEXCEPTION_DATADICTDEL,
				"读写权限不为可删除{$curDate$}.");
		businessException.put(AppConstant.BUSINESSEXCEPTION_DATADICTHASSON,
				"含有子数据项.");
	}

	public String getDataViewRoleTypeValue(String id) {
		return dataViewRoleType.get(id);
	}

	public String getBusinessExceptionValue(String id) {
		String businessExceptionMessage = "";
		String[] exceptionId = id.split(",");
		for (int i = 0; i < exceptionId.length; i++) {
			if (!"".equals(exceptionId[i])) {
				businessExceptionMessage += businessException
						.get(exceptionId[i]);
			}
		}
		return businessExceptionMessage;
	}

	public String getDataViewRoleTypeXml() {
		return PubUtil.getSelectXml(dataViewRoleTypeSelect);
	}
}