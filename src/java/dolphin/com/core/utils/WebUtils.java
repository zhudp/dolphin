package com.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: wanglf
 * @since: Dec 19, 2007 2:00:30 PM
 */
/** 
 ************************************************
 * @file: WebUtils.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************
 * @package: com.hs.core.utils
 * @class: WebUtils
 * @description: 
 * 
 * @author: Administrator
 * @since: 2008-6-14-10:39:42
 * @history:
**/
public class WebUtils {

	private WebUtils() {
	}

	/**
	 * 重载Spring WebUtils中的函数,作用如函数名所示 加入泛型转换,改变输入参数为request 而不是session
	 * 
	 * @param name
	 *            session中变量名称
	 * @param clazz
	 *            session中变量的类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getOrCreateSessionAttribute(HttpServletRequest request, String name, Class<T> clazz) {
		return (T) org.springframework.web.util.WebUtils.getOrCreateSessionAttribute(request.getSession(), name, clazz);
	}
	
	// ---------------------------------------------------------------------- 后台往前台打印消息
	
	
	/************************************************ 
	 * @method：getExtFailureMessage  
	 * @param message
	 * @return 
	 * @description：当弹出单条信息时，组装Json
	
	 * @create:2008-6-14-10:39:26 lilq 
	 * 
	 */
	public static String getExtFailureMessage(String message) {
		return "{failure:true, data:['" + message + "'],multi:['false']}";
	}
    /************************************************ 
	 * @method：getExtMultiFailureMessage  
	 * @param message
	 * @return 
	 * @description：当弹出多条信息时组装Json
	
	 * @create:2008-6-14-10:40:31 lilq 
	 * 
	 */
	public static String getExtMultiFailureMessage(String message) {
		return "{failure:true, data:['" + message + "'],multi:['true']}";
	}
	public static String getExtSuccuseMessage() {
		return getExtSuccuseMessage(null);
	}

	public static String getExtSuccuseMessage(String message) {
		return getExtSuccuseMessage(message,false);
	}
	
	
	
	/**
	 * @param checkBoolean	true:向前台返回的是Obj false:向前台返回的是String(默认false)
	 * @return 向前台返回的消息
	 * @create:Oct 20, 2008-9:15:25 AM huangrh
	 */
	public static String getExtSuccuseMessage(String message,Boolean checkBoolean){
		if(checkBoolean){
			return message == null ? "{success:true,data:[]}" : "{success:true,data:" + message + "}"; 
		}
		return message == null ? "{success:true,data:[]}" : "{success:true,data:['" + message + "']}"; 
	}
	/**主要用于JsonStore返回数据，root为images时
	 * @return 向前台返回的消息
	 */
	public static String getExtSuccuseMessageView(String message){
		return message == null ? "{success:true,data:[]}" : "{success:true,data:[" + message + "]}"; 
	}
	
	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	public static String getIpAddrByRequest(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
}
