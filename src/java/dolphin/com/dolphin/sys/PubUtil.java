package com.dolphin.sys;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.core.dao.support.Page;
import com.core.utils.WriteXMLUtil;

/**
 * ***********************************************
 * 
 * @file: PubUtil.java
 * @Copyright: 2007 HundSun Electronics Co.,Ltd. All right reserved.
 *             ***********************************************
 * @package: com.hs.brmims.sys.utils
 * @class: PubUtil
 * @description: 公用方法类
 * 
 * @author: lining
 * @since: 2007-9-3-22:33:37
 * @history:
 */
public class PubUtil {

	/***************************************************************************
	 * @method：formatString
	 * @param obj
	 * @return
	 * @description：
	 * 
	 * 格式化字符，去掉null和"null"
	 * @create:2007-9-13-16:09:56 lining
	 * 
	 */
	public static String formatString(Object obj) {
		DecimalFormat df = new DecimalFormat("#0.00");
		if ((null == obj) || ("null".equals(obj))) {
			return "";
		}
		if (obj instanceof Double) {
			return df.format(obj);
		}
		if (obj instanceof Float) {
			return df.format(obj);
		}
		return obj.toString();
	}

	public static String formatString(Object obj, int length) {
		DecimalFormat df = new DecimalFormat("#0.00");
		if (length == 8) {
			df = new DecimalFormat("#0.00000000");
		} else if (length == 4) {
			df = new DecimalFormat("#0.0000");
		}
		if ((null == obj) || ("null".equals(obj))) {
			return "";
		}
		if (obj instanceof Double) {
			return df.format(obj);
		}
		if (obj instanceof Float) {
			return df.format(obj);
		}
		return obj.toString();
	}

	/***************************************************************************
	 * @method：getValueFun
	 * @param fieldList
	 * @param obj
	 * @return
	 * @description：
	 * 
	 * 返回"<obj的属性名>obj的属性值</obj的属性名>"
	 * @create:2007-9-13-16:10:23 lining
	 * 
	 */
	public static String getValueFun(Field[] fieldList, Object obj) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < fieldList.length; j++) {
			try {
				Field fld = fieldList[j];
				if (Modifier.isStatic(fld.getModifiers())) {
					continue;
				}
				String classFieldName = fld.getName();
				sb.append(WriteXMLUtil.writeCDATAElement(classFieldName,
						formatString(DataObjUtils.getBeanPropertyValue(obj,
								classFieldName))));

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String getHighPrecisionValueFun(Field[] fieldList, Object obj) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < fieldList.length; j++) {
			try {
				Field fld = fieldList[j];
				if (Modifier.isStatic(fld.getModifiers())) {
					continue;
				}
				String classFieldName = fld.getName();
				if (classFieldName.equals("unitFee")
						|| classFieldName.equals("unitPrice")) {
					sb.append(WriteXMLUtil.writeCDATAElement(classFieldName,
							formatString(DataObjUtils.getBeanPropertyValue(obj,
									classFieldName), 8)));
				} else if (classFieldName.equals("yearFee")) {
					sb.append(WriteXMLUtil.writeCDATAElement(classFieldName,
							formatString(DataObjUtils.getBeanPropertyValue(obj,
									classFieldName), 4)));
				} else {
					sb.append(WriteXMLUtil.writeCDATAElement(classFieldName,
							formatString(DataObjUtils.getBeanPropertyValue(obj,
									classFieldName))));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	/***************************************************************************
	 * @method：getValueFun
	 * @param fieldList
	 * @param obj
	 * @param nameMap
	 * @return
	 * @description：
	 * 
	 * 返回"<obj的属性名在nameMap中的value>obj的属性值</obj的属性名在nameMap中的value>",obj的属性名必须在nameMap中存在
	 * 
	 * @create:2007-9-13-16:12:18 lining
	 * 
	 */
	public static String getValueFun(Field[] fieldList, Object obj, Map nameMap) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < fieldList.length; j++) {
			try {
				Field fld = fieldList[j];
				if (fld.getModifiers() == Modifier.STATIC) {
					continue;
				}
				String classFieldName = fld.getName();
				if (nameMap.get(classFieldName) != null) {
					sb.append(WriteXMLUtil.writeCDATAElement(nameMap.get(
							classFieldName).toString(),
							formatString(DataObjUtils.getBeanPropertyValue(obj,
									classFieldName))));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String makeXML(List list, Integer count) {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Items>");
		sb.append("<TotalResults>" + count + "</TotalResults>");
		for (int i = 0; i < list.size(); i++) {
			Object temp = list.get(i);
			sb.append("<Item>");
			Class cls = temp.getClass();
			Field fieldList[] = cls.getDeclaredFields();
			sb.append(getValueFun(fieldList, temp));
			sb.append("</Item>");
		}
		sb.append("</Items>");
		return sb.toString();
	}
	public static String makeXMLByPage(Page page) {
		List list=(List)page.getData() ;
		Long count=page.getTotalCount();
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Items>");
		sb.append("<TotalResults>" + count + "</TotalResults>");
		for (int i = 0; i < list.size(); i++) {
			Object temp = list.get(i);
			sb.append("<Item>");
			Class cls = temp.getClass();
			Field fieldList[] = cls.getDeclaredFields();
			sb.append(getValueFun(fieldList, temp));
			sb.append("</Item>");
		}
		sb.append("</Items>");
		return sb.toString();
	}


	public static String makeFeeListXML(List list, Integer count) {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Items>");
		sb.append("<TotalResults>" + count + "</TotalResults>");
		for (int i = 0; i < list.size(); i++) {
			Object temp = list.get(i);
			sb.append("<Item>");
			Class cls = temp.getClass();
			Field fieldList[] = cls.getDeclaredFields();
			sb.append(getHighPrecisionValueFun(fieldList, temp));
			sb.append("</Item>");
		}
		sb.append("</Items>");
		return sb.toString();
	}


	public static String makeXML(ArrayList list, HashMap nameMap, Integer count) {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Items>");
		sb.append("<TotalResults>" + count + "</TotalResults>");
		for (int i = 0; i < list.size(); i++) {
			Object temp = list.get(i);
			sb.append("<Item>");
			Class cls = temp.getClass();
			Field fieldList[] = cls.getDeclaredFields();
			sb.append(getValueFun(fieldList, temp, nameMap));
			sb.append("</Item>");
		}
		sb.append("</Items>");
		return sb.toString();
	}


	public static String makeSelectXML(List list, Map nameMap) {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Items>");
		for (int i = 0; i < list.size(); i++) {
			Object temp = list.get(i);
			sb.append("<Item>");
			Class cls = temp.getClass();
			Field fieldList[] = cls.getDeclaredFields();
			sb.append(getValueFun(fieldList, temp, nameMap));
			sb.append("</Item>");
		}
		sb.append("</Items>");
		return sb.toString();
	}



	public static void setAttribute(HttpServletRequest request, Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (Modifier.isStatic(fields[i].getModifiers())) {
				continue;
			}
			try {
				request.setAttribute(fields[i].getName(), DataObjUtils
						.getBeanPropertyValue(obj, fields[i].getName()));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	public static String makeObjectXML(Object obj) {
		if (null == obj)
			return "";
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Items>");
		sb.append("<Item>");
		Class cls = obj.getClass();
		Field fieldList[] = cls.getDeclaredFields();
		sb.append(getValueFun(fieldList, obj));
		sb.append("</Item>");
		sb.append("</Items>");
		return sb.toString();
	}

	public static String makeObjectXML(Object obj, Map nameMap) {
		if (null == obj)
			return "";
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Items>");
		sb.append("<Item>");
		Class cls = obj.getClass();
		Field fieldList[] = cls.getDeclaredFields();
		sb.append(getValueFun(fieldList, obj, nameMap));
		sb.append("</Item>");
		sb.append("</Items>");
		return sb.toString();
	}



	public static String makeXMLNotNull(ArrayList list, Integer count) {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Items>");
		sb.append("<TotalResults>" + count + "</TotalResults>");
		for (int i = 0; i < list.size(); i++) {
			Object temp = list.get(i);
			Class cls = temp.getClass();
			sb.append("<Item>");
			Field fieldList[] = cls.getDeclaredFields();
			sb.append(getValueFun(fieldList, temp));
			sb.append("</Item>");
		}
		sb.append("</Items>");
		return sb.toString();
	}



	public static String getSelectXml(Map map) {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Items>");
		Set set = map.entrySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			sb.append("<Item>");
			Map.Entry entry = (Map.Entry) iter.next();
			sb.append("<id>" + entry.getKey() + "</id>");
			sb.append("<value>" + entry.getValue() + "</value>");
			sb.append("</Item>");
		}
		sb.append("</Items>");
		return sb.toString();
	}

	/***************************************************************************
	 * @method：getFullDay
	 * @param inDate
	 * @param months
	 * @return
	 * @description：
	 * 
	 * 获得传入日期的月闭合日期（参数months决定闭合月数）
	 * 
	 * 如inDate：2007-09-01，months：1，返回2007-09-30
	 * 如inDate：2007-09-01，months：12，返回2008-08-31
	 * @create:2007-9-16-16:10:01 lining
	 * 
	 */
	public static Date getFullDate(Date inDate, int months) {
		// 记住不要用Date.setMonth等方法，已经不被推荐使用，会出现计算错误，要用GregorianCalendar
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(inDate);
		calendar.add(Calendar.MONTH, months);
		calendar.add(Calendar.DATE, -1);
		return new Date(calendar.getTime().getTime());
	}

}
