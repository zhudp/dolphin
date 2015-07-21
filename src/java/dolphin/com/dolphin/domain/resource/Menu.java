package com.dolphin.domain.resource;

import org.apache.commons.lang.StringUtils;

/**
 * ***********************************************
 *
 * @file: Menu.java
 * @Copyright: 2007 HundSun Electronics Co.,Ltd. All right reserved.
 *             ***********************************************
 * @package: com.hs.dolphin.domain.resource
 * @class: Menu
 * @description: 菜单项
 * @author: chennp
 * @since: 2008-6-20-上午09:25:00
 * @history:
 */
public class Menu extends Resource {
	private static final long serialVersionUID = 7699669189502339807L;

	@Override
	public String toJSONString() {
		return toJSONString(false);
	}
	@Override
	public String toJSONString(boolean haschecked){
		StringBuffer treeResourceJs = new StringBuffer();
		treeResourceJs.append("{");
		if (StringUtils.isNotEmpty(this.getUrl())){
			treeResourceJs.append("\"href\":\"" + this.getUrl() + "\",");
		}
		if(haschecked){
			treeResourceJs.append("\"checked\":" + this.isHasOwner() + ",");
		}
		treeResourceJs.append("\"id\":\"" + this.getId() + "\",");
		treeResourceJs.append("\"text\":\"" + this.getText() + "\",");
		treeResourceJs.append("\"desc\":\"" + this.getDesc() + "\",");
		treeResourceJs.append("\"contextPath\":\"" + this.getContextPath() + "\",");
		treeResourceJs.append("\"code\":\"" + this.getCode() + "\",");
		treeResourceJs.append("\"level\":\""+this.getLevel()+"\"," );
		if (this.hasChild()) {
			treeResourceJs.append("\"iconCls\":\"" + "icon-pkg" + "\",");
			treeResourceJs.append("\"cls\":\"" + "package" + "\",");
			treeResourceJs.append("\"singleClickExpand\":true,");
			treeResourceJs.append("\"children\":[");
			for (Resource subResource : this.getSubResources()) {
				treeResourceJs.append(subResource.toJSONString(haschecked));
			}
			if (this.getSubResources().size() > 0) {
				treeResourceJs.deleteCharAt(treeResourceJs.length() - 1);
			}
			treeResourceJs.append("]},");
		} else {
			treeResourceJs.append("\"isClass\":true,");
			treeResourceJs.append("\"iconCls\":\"" + "icon-cls" + "\",");
			treeResourceJs.append("\"cls\":\"" + "cls" + "\",");
			treeResourceJs.append("\"leaf\":true");
			treeResourceJs.append("},");
		}

		return treeResourceJs.toString();
	}
}
