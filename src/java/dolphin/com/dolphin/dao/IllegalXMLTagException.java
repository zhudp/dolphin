package com.dolphin.dao;

/***********************************************************************************************************
 * 非法XML标签异常类
 * 
 * @author: chennp
 * @since: 2008-7-4 下午02:09:42
 * @history: ***********************************************
 * @file: IllegalXMLTagException.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 **********************************************************************************************************/
public class IllegalXMLTagException extends RuntimeException {
	/**  */
	private static final long serialVersionUID = 1L;

	public IllegalXMLTagException() {
		super();
	}

	public IllegalXMLTagException(String message) {
		super(message);
	}

	public IllegalXMLTagException(Throwable cause) {
		super(cause);
	}

	public IllegalXMLTagException(String message, Throwable cause) {
		super(message, cause);
	}

}
