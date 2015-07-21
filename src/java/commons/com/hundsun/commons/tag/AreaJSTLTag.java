package com.hundsun.commons.tag;

import java.util.List;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.dolphin.domain.Datadict;
import com.dolphin.service.DatadictService;

/**
 * 自定义JSTL标签
 * @author: zhudp
 * @since: 2012-7-29  下午02:28:21
 * @history:
 ************************************************
 * @file: FormatTag.java
 * @Copyright: 2010 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ***********************************************
 */
public class AreaJSTLTag {
	
	static WebApplicationContext context ;
	static {
		if(context == null) {
			context = ContextLoaderListener.getCurrentWebApplicationContext();
		}
	}

	/**
	 * 派出所组装成ext的下拉框
	 */
	public static String buildComboxData4Police() {
		DatadictService datadictService =  (DatadictService) context.getBean("datadictService");
		List<Datadict> list = datadictService.getListByResType("POLICE");
		for(int i=0;i<list.size();i++){
			Datadict d = list.get(i);
			if(d.getResCode() == null)
			list.remove(d);
		}
		return getJsonCombox4Police(list);
	}
	
	private static String getJsonCombox4Police(List<Datadict> list){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("{\"result\":[");
		for (Datadict d : list) {
			String id = d.getResCode().toString();
			String text = d.getResName();
			strBuffer.append("{\"id\":\"" + id + "\",\"text\":\"" + text+ "\"},");
		}
		String buffer = strBuffer.substring(0, strBuffer.length()-1);
		buffer = buffer + "],\"totalCount\":" + list.size() + "}";
		return buffer;
	}
	
}
