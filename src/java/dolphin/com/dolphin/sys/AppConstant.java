package com.dolphin.sys;

public final class AppConstant {
	// 操作类型
	public static final String OPER_TYPE_ADD = "add";// 新增

	public static final String OPER_TYPE_MODIFY = "modify";// 修改

	public static final String OPER_TYPE_DELETE = "delete";// 删除

	// 日志类型
	public static final String SYSTEM_LOG = "0"; // 系统日志

	public static final String SENSTIVE_LOG = "1"; // 业务日志

	// 数据操作角色类型
	public static final String DATA_VIEW_ROLE_TYPE_ALL = "0";// 全部

	public static final String DATA_VIEW_ROLE_TYPE_DEPT = "1";// 按指定商位察看

	public static final String DATA_VIEW_ROLE_TYPE_SELF = "2";// 按市场察看

	// 数据操作角色修改标志
	public static final String DATA_VIEW_ROLE_FLAG_CAN_DEL_AND_MODIFY = "0";// 可修改删除

	public static final String DATA_VIEW_ROLE_FLAG_CAN_MODIFY = "1";// 可以修改不可以删除

	public static final String DATA_VIEW_ROLE_FLAG_CAN_NOT_DEL_AND_MODIFY = "2";// 不可修改删除

	// 数据字典数据分类
	public static final String DATA_DICT_FLOOR = "4"; // 楼层

	// 数据字典数据分类
	public static final String DATA_DICT_BLOCK = "3"; // 区块

	// 数据字典修改标志位

	public static final String DATA_DICT_READONLY = "1"; // 只读

	public static final String DATA_DICT_CAN_MODIFY = "2"; // 可修改不可删除

	public static final String DATA_DICT_CAN_MODIFY_AND_DEL = "3";// 可修改删除

	public static final String APPLY_YES = "1"; // 启用

	public static final String APPLY_NO = "2"; // 未启用

	/** 没有数据权限 */
	public static final String NO_DATAROLE = "NO_DATAROLE";

	/** 业务异常类型 */
	public static final String BUSINESSEXCEPTION_ACCOUNTISUSEING = "0"; // 登录帐号已被使用

	public static final String BUSINESSEXCEPTION_NOACCOUNT = "1"; // 登录帐号已被使用

	public static final String BUSINESSEXCEPTION_PASSWORDERROE = "2"; // 登录密码错误

	public static final String BUSINESSEXCEPTION_OLDPASSWORDERROE = "3"; // 修改密码时，旧密码错误

	public static final String BUSINESSEXCEPTION_NEWPASSWORDERROE = "4"; // 修改密码时，新密码必须为大于六位的英文或数字

	public static final String BUSINESSEXCEPTION_DATADICTDEL = "5"; // 数据字典的读写权限为不可删除

	public static final String BUSINESSEXCEPTION_DATADICTHASSON = "6"; // 数据字典含有子节点

}
