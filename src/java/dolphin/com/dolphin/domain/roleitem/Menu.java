package com.dolphin.domain.roleitem;

import java.util.List;

/**
 * 功能菜单.
 * 
 * @author: wanglf
 * @since: Feb 3, 2008 10:53:27 AM
 */
public class Menu extends Resource {
	private static final long serialVersionUID = 7699669189502339807L;

	private String url;
	private List<Menu> subMenus;// 子菜单

	public Menu() {
		super();
	}

	public Menu(String id,Integer code, String text, String url) {
		super();
		this.setId(id);
		this.setCode(code);
		this.setText(text);
		this.url = url;
	}

	/**
	 * 当前Menu作为树的一部分，返回相应的js代码，以便树的主体部分组装成一棵完整的树.
	 */
	public StringBuffer getTreeMenuJs() {
		StringBuffer treeMenuJs = new StringBuffer();
		// 非叶子
		if (getSubMenus() != null) {
			treeMenuJs.append("{");
			treeMenuJs.append("\"id\":\"" + this.getId() + "\",");
			treeMenuJs.append("\"iconCls\":\"" + "icon-pkg" + "\",");
			treeMenuJs.append("\"cls\":\"" + "package" + "\",");
			treeMenuJs.append("\"text\":\"" + this.getText() + "\",");
			treeMenuJs.append("\"singleClickExpand\":true,");
			treeMenuJs.append("\"children\":[");

			List<Menu> subMenus = getSubMenus();
			for (Menu subMenu : subMenus) {
				treeMenuJs.append(subMenu.getTreeMenuJs());
			}
			if (subMenus.size() > 0) {
				treeMenuJs.deleteCharAt(treeMenuJs.length() - 1);
			}
			treeMenuJs.append("]},");
		}
		// 叶子
		else {
			treeMenuJs.append("{");
			treeMenuJs.append("\"href\":\"" + this.getUrl() + "\",");
			treeMenuJs.append("\"id\":\"" + this.getText() + "\",");
			treeMenuJs.append("\"iconCls\":\"" + "icon-cls" + "\",");
			treeMenuJs.append("\"isClass\":true,");
			treeMenuJs.append("\"cls\":\"" + "cls" + "\",");
			treeMenuJs.append("\"text\":\"" + this.getText() + "\",");
			treeMenuJs.append("\"leaf\":true");
			treeMenuJs.append("},");
		}
//System.out.println(treeMenuJs);
		return treeMenuJs;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
		this.setSubChildren(subMenus);
	}

	@Override
	public String getHasChild() {
		if (getSubChildren() != null && getSubChildren().size() != 0)
			return  "Y";

		return null;
	}
}
