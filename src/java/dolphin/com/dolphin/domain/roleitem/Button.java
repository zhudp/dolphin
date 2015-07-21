package com.dolphin.domain.roleitem;

/**
 * 功能按钮.
 * 
 * @author: wanglf
 * @since: Feb 3, 2008 10:56:18 AM
 */
public class Button extends Resource {

	private static final long serialVersionUID = 7555559085821137337L;
	private String event;
	public Button() {
		super();
	}

	public Button(String id,Integer code, String text) {
		this.setId(id);
		this.setCode(code);
		this.setText(text);
	}
	public Button(String id,Integer code, String text,String url) {
		this(id,code,text);
		this.setUrl(url);
	}
	public Button(String id,Integer code, String text,String url,String event) {
		this(id,code,text,url);
		this.setEvent(event);
	}
	@Override
	public String getHasChild() {
		return null;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
}
