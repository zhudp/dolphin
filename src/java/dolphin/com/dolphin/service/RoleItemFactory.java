package com.dolphin.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.core.utils.XmlUtils;
import com.dolphin.domain.roleitem.Button;
import com.dolphin.domain.roleitem.Menu;
import com.dolphin.domain.roleitem.Resource;


/**
 * 角色元素工厂类.
 * </p>
 * This class is Singleton.
 * 
 * @author: wanglf
 * @since: Feb 3, 2008 12:51:05 PM
 */
@SuppressWarnings("unchecked")
public class RoleItemFactory {
	private static RoleItemFactory instance = null;

	private static List<Menu> menuList = new ArrayList<Menu>();

	private static final String MENU = "menu";

	private static final String BUTTON = "button";
	
	private static final String CODE = "code";
	
	private static final String ID = "id";
	
	private static final String URL = "url";
	
	private static final String TEXT = "text";
	
	private static final String EVENT = "event";
	/**
	 * 返回RoleItemFactory实体.
	 */
	public static RoleItemFactory getInstance() {
		if (null == instance) {
			instance = new RoleItemFactory();
		}

		return instance;
	}

	/**
	 * 私有构造函数. 读取RoleItem定义Xml文件.
	 */
	private RoleItemFactory() {
		Element rootElement = XmlUtils.getRootElement("RoleItemDefine.xml",
				RoleItemFactory.class);
		menuList = getSubMenus(rootElement);
	}

	/**
	 * 根据当前Element获取菜单，同时递归获取菜单子元素.
	 */
	private Menu getMenuFromElement(Element element, Element parentElement) {
		Menu menu = creatMenu(element);
		menu.setParentMenu(creatMenu(parentElement));
		menu.setSubMenus(getSubMenus(element));
		menu.setSubChildren(getSubButtons(element, menu));

		return menu;
	}

	private Menu creatMenu(Element element) {
		if (element.getAttributeValue(CODE) == null)
			return new Menu();
		
		String id = element.getAttributeValue(ID);
		Integer code = Integer.valueOf(element.getAttributeValue(CODE));
		String text = element.getAttributeValue(TEXT);
		String url = element.getAttributeValue(URL);
		return new Menu(id,code,text, url);
	}

	/**
	 * 根据当前Element获取子菜单.
	 */
	@SuppressWarnings("unchecked")
	private List<Menu> getSubMenus(Element element) {
		List<Menu> subMenus = null;

		List<org.jdom.Element> subElements = element.getChildren(MENU);
		if (subElements != null && subElements.size() != 0) {
			subMenus = new ArrayList<Menu>(subElements.size());
			for (Element subElement : subElements) {
				subMenus.add(getMenuFromElement(subElement, element));
			}
		}

		return subMenus;
	}

	/**
	 * 根据当前Element获取子按钮.
	 */
	@SuppressWarnings("unchecked")
	private List<Button> getSubButtons(Element element, Menu parentMenu) {
		List<Button> subButtons = null;

		List<org.jdom.Element> subElements = element.getChildren(BUTTON);
		if (subElements != null && subElements.size() != 0) {
			subButtons = new ArrayList<Button>(subElements.size());
			for (Element subElement : subElements) {
				Button button = buildButtonFromElement(subElement);
				button.setParentMenu(parentMenu);
				subButtons.add(button);
			}
		}

		return subButtons;
	}

	private Button buildButtonFromElement(Element element) {
		String id = element.getAttributeValue(ID);
		Integer code = Integer.valueOf(element.getAttributeValue(CODE));
		String text = element.getAttributeValue(TEXT);
		String url = element.getAttributeValue(URL);
		String event = element.getAttributeValue(EVENT);
		return new Button(id,code,text,url,event);
	}

	public List getMenuList() {
		return menuList;
	}

	public Integer getMaxItemLevel() {
		return getMaxItemLevel(Integer.valueOf(0), menuList);
	}

	private Integer getMaxItemLevel(Integer maxLevel, List roleItemList) {
		if (roleItemList == null)
			return maxLevel;

		for (Iterator iterator = roleItemList.iterator(); iterator.hasNext();) {
			Resource roleItem = (Resource) iterator.next();

			if (roleItem.getLevel() > maxLevel)
				maxLevel = roleItem.getLevel();

			maxLevel = getMaxItemLevel(maxLevel, roleItem.getSubChildren());
		}

		return maxLevel;
	}
}
