package com.dolphin.domain.roleitem;

import java.util.List;

import com.core.BaseEntity;

/**
 * 功能元素.
 * 
 * @author: wanglf
 * @since: Feb 16, 2008 10:11:07 PM
 */
@SuppressWarnings("unchecked")
public abstract class Resource extends BaseEntity {
	private static final long serialVersionUID = 4165871525354918434L;

	private String id;
	private Integer code;
	private String text;
	private String url;
	private Integer level;
	private Menu parentMenu;
	private List subChildren;

	public Resource() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLevel() {
		if (getCode() != null && getCode().intValue() != 0) {
			level = getCode().toString().length();
		}
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public List getSubChildren() {
		return subChildren;
	}

	public void setSubChildren(List subChildren) {
		if (this.subChildren == null)
			this.subChildren = subChildren;
		else if (subChildren != null)
			this.subChildren.addAll(subChildren);
	}

	/** 是否存在小孩 */
	public abstract String getHasChild();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
