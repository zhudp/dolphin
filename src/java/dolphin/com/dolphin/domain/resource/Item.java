package com.dolphin.domain.resource;

import org.apache.commons.lang.StringUtils;


/** 
 * 链接项类
 * @author: chennp
 * @since: 2008-7-4  上午11:13:36
 * @history:
 ************************************************
 * @file: Item.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************/
public class Item extends Resource {

	private static final long serialVersionUID = 7555559085821137337L;
	
	private String handler;//前提js函数名
	
	public Item() {
		super();
	}

	public Item(String id,String code, String text) {
		this.setId(id);
		this.setCode(code);
		this.setText(text);
	}
	public Item(String id,String code, String text,String url) {
		this(id,code,text);
		this.setUrl(url);
	}
	public Item(String id,String code, String text,String url,String handler) {
		this(id,code,text,url);
		this.setHandler(handler);
	}

	public String getHandler() {
		if(StringUtils.isBlank(this.handler))
			return StringUtils.EMPTY;
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}


	@Override
	public String toJSONString() {
		return toJSONString(false);
	}
	@Override
	public String toJSONString(boolean haschecked){
		StringBuffer jsonString = new StringBuffer();
		jsonString.append("{");
		jsonString.append("\"id\":\""+this.getId()+"\",");
		jsonString.append("\"itemId\":\""+this.getCode()+"\",");
		jsonString.append("\"text\":\""+this.getText()+"\",");
		jsonString.append("\"handler\":\""+this.getHandler()+"\",");
		jsonString.append("\"icon\":\""+this.getIconPath()+"\",");
		if(haschecked){
			jsonString.append("\"checked\":"+this.isHasOwner()+",");
		}
		jsonString.append("\"url\":\""+this.getUrl()+"\",");
		jsonString.append("\"isClass\":true,");
		jsonString.append("\"iconCls\":\"" + "icon-cls" + "\",");
		jsonString.append("\"cls\":\"" + "cls" + "\",");
		jsonString.append("\"leaf\":true");
		if(StringUtils.isNotBlank(this.getConfig())){
			jsonString.append(","+this.getConfig());
		}
		jsonString.append("},");
		return jsonString.toString();
	}

}
