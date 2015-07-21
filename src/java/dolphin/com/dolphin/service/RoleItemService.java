package com.dolphin.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.dolphin.domain.roleitem.Menu;



/**
 * 角色权限元素业务实现.
 * 
 * @author: wanglf
 * @since: Feb 3, 2008 11:27:55 AM
 * @see com.hs.common.service.RoleItemService
 */
@Service
public class RoleItemService {
	protected static final Log log = LogFactory
			.getLog(RoleItemService.class);
	
	public String getTreeFromMenus(List<Menu> menus) {
		StringBuffer treeMenuJs = new StringBuffer();
		treeMenuJs.append("<script type=\"text/javascript\">\n");
		treeMenuJs
				.append("Docs.classData = {\"id\":\"apidocs\",\"iconCls\":\"icon-pkg\",\"text\":\"系统主菜单\",\"singleClickExpand\":true,\"children\":[");

		if (menus != null) {
			for (Menu menu : menus) {
				treeMenuJs.append(menu.getTreeMenuJs());
			}
			if (menus.size() > 0) {
				treeMenuJs.deleteCharAt(treeMenuJs.length() - 1);
			}
		}

		treeMenuJs.append("],\"pcount\":1};");
		treeMenuJs.append("Docs.icons = {};");
		treeMenuJs.append("</script>\n");
		log.debug(treeMenuJs.toString());
		return treeMenuJs.toString();
	}

	public String getTreeTestMeuns(List<Menu> menus) {
		StringBuffer treeMenuJs = new StringBuffer();
		treeMenuJs.append("<script type=\"text/javascript\">\n");
		if (menus != null) {
			for (Menu menu : menus) {
				treeMenuJs.append(menu.getTreeMenuJs());
			}
			if (menus.size() > 0) {
				treeMenuJs.deleteCharAt(treeMenuJs.length() - 1);
			}
		}
		treeMenuJs.append("</script>\n");
		log.debug(treeMenuJs.toString());
		return treeMenuJs.toString();
	}

	public String getButtonsByMenuId(String menuID) {
		return null;
	}
}
