package com.dolphin.sys.log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.SystemLog;
import com.dolphin.domain.User;
import com.dolphin.service.SystemLogService;

/**
 * ***********************************************
 * 
 * @file: LogAspect.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 *             ***********************************************
 * @package: com.hs.common.sys
 * @class: LogAspect
 * @description:
 * @author: lilq
 * @since: 2008-5-10-15:45:05
 * @history: 2008年7月4日 11时38分28秒 袁聪 增加了在spring中配置要记录到日志的对象的属性功能
 */
@Component
public class LogAspect {

	private String userIpAddress; // 当前操作员IP地址

	private Map operTypeSet;

	private Map operTypeName;

	private LogBuffer logBuffer = LogBuffer.getInstance();

	private Map obj_method;
	@Autowired
	private SystemLogService systemLogService;

	public void insertPointcut() {
	}

	public void insertLogInfo(JoinPoint joinPoint)
			throws ClassNotFoundException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		User user = RemoteUser.get();
		if (joinPoint.getTarget().getClass().toString().indexOf("SystemLog") > -1) {
			return;
		}
		SystemLog systemLog = new SystemLog();
		if (null != user) {
			systemLog.setUserId(user.getAccount());
			systemLog.setIpAdd(userIpAddress);
		}
		systemLog.setOperType(getOperType(joinPoint.getSignature().getName()));
		String strlogComment = "函数metod:【" + joinPoint.getSignature().getName()
				+ "】; 参数集 ：";
		// Object objArgsObject = joinPoint.getArgs()[0];// 取得第一个参数对象
		// strlogComment += getObjLogData(objArgsObject);// 获取这个参数对象里有记录到日志的属性及值
		for (Object objArgsObject : joinPoint.getArgs()) {// 遍历所有参数，如果配置了记录的，
			// 就记录到日志里。
			// if (isArryType(objArgsObject) || isListType(objArgsObject) ||
			// isMapType(objArgsObject)) {
			// for (Object objObject : (Object[]) objArgsObject) {
			// strlogComment += getObjLogData(objObject);// 获取这个参数对象里有记录到日志的属性及值
			// }
			// } else {
			// strlogComment += getObjLogData(objArgsObject);//
			// 获取这个参数对象里有记录到日志的属性及值
			// }
			strlogComment += getObjLogData(objArgsObject);// 获取这个参数对象里有记录到日志的属性及值
		}
		Integer maxCount = 760;// 数据库字段长度是800
		if (strlogComment.length() > maxCount) {
			strlogComment = strlogComment.substring(0, maxCount) + "......";
		}
		systemLog.setSlogComment(strlogComment);// 将配置好的对象的属性记录到日志中.
		systemLog.setOperObjec(joinPoint.getTarget().getClass().toString());
		systemLog.setSlogType("");
		setSystemLogService(systemLogService);
		logBuffer.insertLogToBuffer(systemLog);// 将日志插入到buffer里
		// entityService.insertSystemLog(systemLog);
	}

	/**
	 * 获取这个参数对象里要记录到日志的属性及值
	 * 
	 * @param obj
	 * @author: 袁聪
	 */
	private String getObjLogData(Object obj) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (null == obj) {
			return "";
		}
		if (isArryType(obj)) {
			String strlogComment = "{";
			for (Object objObject : (Object[]) obj) {
				strlogComment += getObjLogData(objObject);// 获取这个参数对象里有记录到日志的属性及值
			}
			strlogComment = strlogComment + "};  ";
			return strlogComment;
		} else if (isListType(obj)) {
			String strlogComment = "{";
			for (Object objObject : (List) obj) {
				strlogComment += getObjLogData(objObject);// 获取这个参数对象里有记录到日志的属性及值
			}
			strlogComment = strlogComment + "};  ";
			return strlogComment;
		} else if (isMapType(obj)) {
			String strlogComment = "{";
			Map map = (Map) obj;
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				strlogComment += getObjLogData(value);// 获取这个参数对象里有记录到日志的属性及值
			}
			strlogComment = strlogComment + "};  ";
			return strlogComment;
		}
		// 如果找到其在spring里配置的记录,就执行配置的记录,否则执行默认配置
		// 获取这个对象要记录到日志的属性名字(KEY)和它的get方法名字(如果是标准的get方法,...)
		String strDataString = "";
		String objName = obj.getClass().getName();
		// 遍历所有的参数，下面的功能就不用了。
		if (isBaseType(obj)) {
			return "[" + obj.toString() + "],";
		}
		Map methodMap = obj_method.containsKey(objName) ? ((Map) obj_method
				.get(objName)) : ((Map) obj_method.get("default"));
		Method[] objmethods = obj.getClass().getDeclaredMethods(); // 得到CLASS的所有方法数组
		if (null != objmethods && 0 < objmethods.length) {
			for (int j = 0; j < objmethods.length; j++) {
				String methodName = objmethods[j].getName(); // 从方法数组中取一个方法名出来
				if (methodMap.containsKey(methodName)) // 如果spring中配置了要记录这个属性的方法,
				// 就记录
				{
					Object strValue = objmethods[j].invoke(obj, new Object[0]); // 运行对象obj1的方法并返回值
					strDataString += obj.getClass().getSimpleName() + "."
							+ methodMap.get(methodName).toString() + "="
							+ strValue + "  ，  ";
					// 将配置好的对象的属性记录到日志中.
				}
			}// end for
			return strDataString;
		}
		return "";
	}

	/**
	 * 判断这个对象的类型是不是基本类型，如int String float等
	 * 
	 * @create Jul 4, 2008 11:46:56 AM 袁聪
	 */
	private boolean isBaseType(Object obj) {
		String objType = obj.getClass().getName();
		objType = objType.trim().substring(0, 9);
		if ("java.lang".equals(objType))
			return true;
		return false;
	}

	private boolean isArryType(Object obj) {
		String objType = obj.getClass().getName();
		objType = objType.trim().substring(0, 2);
		if ("[L".equals(objType))
			return true;
		return false;
	}

	private boolean isListType(Object obj) {
		String objType = obj.getClass().getName();
		if ("java.util".equals(objType.trim().substring(0, 9))
				&& objType.toLowerCase().trim().endsWith("list"))
			return true;
		return false;
	}

	private boolean isMapType(Object obj) {
		String objType = obj.getClass().getName();
		if ("java.util".equals(objType.trim().substring(0, 9))
				&& objType.toLowerCase().trim().endsWith("map"))
			return true;
		return false;
	}

	public static void main(String[] args) {
		LogAspect logAspect = new LogAspect();
		LogAspect logAspect2 = new LogAspect();
		String[] strings = { "s1", "s2" };
		LogAspect[] logAspects = { logAspect, logAspect2 };
		List<String> stringList = new ArrayList<String>();
		stringList.add("stringList1");
		stringList.add("stringList2");
		Map<String, String> strMap = new HashMap<String, String>();
		System.out.println("logAspect.isArryType(strings):"
				+ logAspect.isArryType(strings));
		System.out.println("logAspect.isArryType(logAspects):"
				+ logAspect.isArryType(logAspects));
		System.out.println("logAspect.isBaseType(strings):"
				+ logAspect.isBaseType(strings.getClass().getName()));
		System.out.println("logAspect.isListType(stringList):"
				+ logAspect.isListType(stringList));
		System.out.println("logAspect.isMapType(strMap):"
				+ logAspect.isMapType(strMap));
	}

	/**
	 * 判断这个方法的操作类型
	 * 
	 * @param method
	 *            要判断的方法名称
	 * @return String 返回操作类型
	 */
	private String getOperType(String method) {
		String operType = "";
		// 以配置中的切点方法对应类型来解析类型
		Set set = this.operTypeSet.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			if (method.indexOf(entry.getKey().toString().trim()) > -1) {
				operType = entry.getValue().toString().trim();
				break;
			}
		}
		// if (method.indexOf("insert") > -1) {
		// operType = AppConstant.OPER_TYPE_ADD;
		// } else if (method.indexOf("update") > -1 || method.indexOf("init") >
		// -1) {
		// operType = AppConstant.OPER_TYPE_MODIFY;
		// } else if (method.indexOf("remove") > -1 || method.indexOf("delete")
		// > -1) {
		// operType = AppConstant.OPER_TYPE_DELETE;
		// }
		if (null == operType || "".equals(operType)) {
			operType = "other";
		}
		return operType;
	}

	public void setSystemLogService(SystemLogService entityService) {
		logBuffer.setSystemLogService(entityService);
	}

	public Map getObj_method() {
		return obj_method;
	}

	public void setObj_method(HashMap obj_method) {
		this.obj_method = obj_method;
	}

	public String getUserIpAddress() {
		return userIpAddress;
	}

	public void setUserIpAddress(String userIpAddress) {
		this.userIpAddress = userIpAddress;
	}

	public Map getOperTypeSet() {
		return operTypeSet;
	}

	public void setOperTypeSet(Map operTypeSet) {
		this.operTypeSet = operTypeSet;
	}

	public Map getOperTypeName() {
		return operTypeName;
	}

	public void setOperTypeName(Map operTypeName) {
		this.operTypeName = operTypeName;
	}

	public void setObj_method(Map obj_method) {
		this.obj_method = obj_method;
	}
}
