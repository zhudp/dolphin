package com.dolphin.domain.resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.PatternMatchUtils;

/***********************************************************************************************************
 * 参数配置属性类
 * 
 * @author: chennp
 * @since: 2008-7-4 上午10:54:18
 * @history: ***********************************************
 * @file: ConfigAttribute.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 **********************************************************************************************************/
public class ConfigAttribute extends Resource {
	/**  */
	private static final long serialVersionUID = 1L;
	private String name;// 参数名称
	private String regex;// 参数值正则表达式

	/**
	 * 参数值匹配
	 * 
	 * @param paramValue
	 *            参数值
	 * @return 若regex为null，则抛出异常IllegalArgumentException，反之返回匹配结果
	 * @create 2008-7-4 上午10:54:48 chennp
	 * @history
	 */
	public boolean match(Object paramValue) {
		if (StringUtils.isNotBlank(getRegex())) {
			return PatternMatchUtils.simpleMatch(getRegex(), String.valueOf(paramValue));
//			return Pattern.matches(getRegex(), String.valueOf(paramValue));
		} else {
			throw new IllegalArgumentException("没有定义regex");
		}
	}

	@Override
	public String toJSONString() {
		/*
		 * StringBuffer jsonString = new StringBuffer(); jsonString.append("{");
		 * jsonString.append("\"name\":\""+this.getName()+"\",");
		 * if(StringUtils.isNotBlank(this.getValue())){
		 * jsonString.append("\"value\":\""+this.getValue()+"\""); }else {
		 * jsonString.append("\"regex\":\""+this.getRegex()+"\""); } jsonString.append("}");
		 */
		return null;
	}

	@Override
	public String toJSONString(boolean haschecked) {
		return null;
	}

	@Override
	public Boolean hasChild() {
		return false;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
