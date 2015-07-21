package com.dolphin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.dolphin.dao.ResourceDao;
import com.dolphin.domain.resource.Menu;
import com.dolphin.domain.resource.Resource;

/*******************************************************************************
 * 功能资源service实现类
 * 
 * 
 * @author: chennp
 * @since: 2008-7-4 下午02:43:39
 * @history: ***********************************************
 * @file: ResourceServiceImp.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
@Service
public class ResourceService {
	@Autowired
	private ResourceDao resourceDao;

	public List<Resource> getMenusListByIds(List<String> resourceIdsList) {
		return getOwnResourcesListByIds(resourceDao.getMenuResource(),
				resourceIdsList);
	}

	public List<Resource> getMainResourceListByIds(List<String> resourceIdsList) {
		return getOwnResourcesListByIds(resourceDao.getMainResource(),
				resourceIdsList);
	}

	public List<String> getAllResourceId() {
		List<String> idList = new ArrayList<String>();
		Map map = resourceDao.getURLResourceMap();
		Iterator iter = map.entrySet().iterator();
		Entry entry = null;
		List<Menu> menuList = null;
		Collection<Resource> itemList = null;
		Iterator itemIter = null;
		Resource item = null;
		while (iter.hasNext()) {
			entry = (Entry) iter.next();
			menuList = (List<Menu>) entry.getValue();
			if (menuList != null) {
				for (Resource menu : menuList) {
					idList.add(menu.getId());
					itemList = menu.getSubResources();
					if (itemList != null) {
						itemIter = itemList.iterator();
						while (itemIter.hasNext()) {
							item = (Resource) itemIter.next();
							idList.add(item.getId());
						}
					}

				}
			}
		}

		return idList;
	}

	public List<Resource> getAllResources() {
		List<Resource> returnList = null;
		returnList = resourceDao.getMainResource();
		return returnList;
	}

	public String getAllResourcesJason() {
		String returnValue = null;
		List<Resource> list = getAllResources();
		returnValue = writeMenuJSON(list, false);
		return returnValue;
	}

	/**
	 * 根据resourceIdsList从reourcesList获取所拥有的资源
	 * 
	 * @param reourcesList
	 * @param resourceIdsList
	 * @return
	 * @create 2008-7-8 上午11:20:07 chennp
	 * @history
	 */
	public List<Resource> getOwnResourcesListByIds(List<Resource> reourcesList,
			List<String> resourceIdsList) {
		Collections.sort(resourceIdsList);

		List<Resource> subMenusList = new ArrayList<Resource>();
		for (Resource resource : reourcesList) {
			subMenusList.addAll(getOwnResourcesListByIds(resource,
					resourceIdsList));
		}
		return subMenusList;
	}

	/**
	 * 递归获取其子资源
	 * 
	 * @param resource
	 * @return
	 * @create 2008-7-4 下午03:04:43 chennp
	 * @history
	 */
	private List<Resource> getOwnResourcesListByIds(Resource resource,
			List<String> resourceIdsList) {
		List<Resource> menusList = new ArrayList<Resource>();
		
		if (hasOwnResource(resourceIdsList, resource)
				|| resource.isRootResource()) {
			menusList.add(resource);
		}
		if (resource.hasChild()) {
			List<Resource> subResourceList = new ArrayList<Resource>();
			for (Resource subResource : resource.getSubResources()) {
				subResourceList.addAll(getOwnResourcesListByIds(subResource,
						resourceIdsList));
			}
			resource.setSubResources(subResourceList);
		}
		return menusList;
	}

	public String getTreeFromMenusJS(List<Resource> menusList) {
		StringBuffer treeMenuJs = new StringBuffer();
		treeMenuJs.append("<script type=\"text/javascript\">\n");
		treeMenuJs.append("Docs.classData = ");
		treeMenuJs.append(writeMenuJSON(menusList, false));
		if (menusList.size() == 0) {
			treeMenuJs.append("{}");
		}
		treeMenuJs.append(";");
		treeMenuJs.append("Docs.icons = {};");
		treeMenuJs.append("</script>\n");
		return treeMenuJs.toString();
	}

	public String getUrlMainResourceMapJS(List<String> resourceIdsList) {
		StringBuffer urlResourceMapJs = new StringBuffer("[");
		Map<String, List<Resource>> urlResourceMap = resourceDao
				.getURLResourceMap();
		Iterator<String> uriIter = urlResourceMap.keySet().iterator();
		while (uriIter.hasNext()) {
			String url = uriIter.next();
			List<Resource> reosurceList = urlResourceMap.get(url);
			urlResourceMapJs.append("{\"" + url + "\":[");
			urlResourceMapJs.append(writeMenuJSON(getMainResourcesWithHasOwn(
					reosurceList, resourceIdsList), true));
			urlResourceMapJs.append("]},");
		}
		if (!urlResourceMap.isEmpty()) {
			urlResourceMapJs.deleteCharAt(urlResourceMapJs.length() - 1);
		}
		urlResourceMapJs.append("]");
		return urlResourceMapJs.toString();
	}

	public List<Resource> getMainResourcesWithHasOwn(
			List<Resource> resourceList, List<String> resourceIdsList) {
		if (resourceIdsList.size() > 0) {
			for (Resource resource : resourceList) {
				setHasOwnSubResource(resource, resourceIdsList);
			}
		}
		return resourceList;
	}

	public List<Resource> getMainResourcesWithHasOwn(
			List<String> resourceIdsList) {
		return getMainResourcesWithHasOwn(resourceDao.getMainResource(),
				resourceIdsList);
	}

	/**
	 * 设置是否拥有该资源
	 * 
	 * 
	 * @param resource
	 * @param resourceIdsList
	 * @create 2008-7-4 下午03:06:07 chennp
	 * @history
	 */
	private void setHasOwnSubResource(Resource resource,
			List<String> resourceIdsList) {
		if (StringUtils.isNotBlank(resource.getId())) {
			if (hasOwnResource(resourceIdsList, resource)) {
				resource.setHasOwner(true);
			}
			if (resource.hasChild()) {
				for (Resource subResource : resource.getSubResources()) {
					setHasOwnSubResource(subResource, resourceIdsList);
				}
			}
		}
	}

	public boolean hasOwnResource(List<String> resourceIdsList,
			Resource resource) {
		Assert.notNull(resource, "resource is null");
		Assert.notNull(resourceIdsList, "resourceIdsList is null");

		return StringUtils.isNotEmpty(resource.getId())
				&& Collections.binarySearch(resourceIdsList, resource.getId()) > -1;
	}

	public String getTreeFromMenusWithChecked(List<String> resourceIdsList) {
		return getTreeFromMenusView(getMainResourcesWithHasOwn(resourceIdsList));
	}

	public String getTreeFromMenusView(List<Resource> menusList) {
		clearResourceURL(menusList);
		StringBuffer treeMenuJs = new StringBuffer();
		treeMenuJs.append("[");
		treeMenuJs.append(writeMenuJSON(menusList, true));
		treeMenuJs.append("]");
		return treeMenuJs.toString();
	}

	/**
	 * 清除menusList的URL
	 * 
	 * @param menusList
	 * @create 2008-7-4 下午03:17:06 chennp
	 * @history
	 */
	private void clearResourceURL(List<Resource> menusList) {
		for (Resource resource : menusList) {
			resource.setUrl(null);
			if (resource.hasChild()) {
				clearResourceURL((List<Resource>) resource.getSubResources());
			}
		}
	}

	/**
	 * 列表转化JSON，
	 * 
	 * 
	 * @param menusList
	 *            资源列表
	 * @param isOwnChecked
	 *            是否检查已拥有资源标志
	 * @return
	 * @create 2008-7-4 下午03:17:22 chennp
	 * @history
	 */
	private String writeMenuJSON(List<Resource> menusList, boolean isOwnChecked) {
		StringBuffer treeMenuJs = new StringBuffer();
		for (Resource menu : menusList) {
			treeMenuJs.append(menu.toJSONString(isOwnChecked));
		}
		if (menusList.size() > 0) {
			treeMenuJs.deleteCharAt(treeMenuJs.length() - 1);
		}
		return treeMenuJs.toString();
	}

	public List<Resource> getByURI(String uri) {
		Assert.hasText(uri);
		return resourceDao.getByURL(uri);
	}
	
	public List<Resource> getAllUrl() {
		return resourceDao.queryByURL("",true);
	}

	public List<Resource> queryByURI(String url) {
		Assert.hasText(url);
		return resourceDao.queryByURL(url,false);
	}

	public Resource get(String id) {
		Assert.hasText(id);

		return resourceDao.get(id);
	}

	public boolean isExsitResourceDefinition(String URL) {
		Assert.hasText(URL, " the url  is empty!");

		return this.queryByURI(URL).size() > 0;
	}

	/**
	 * 初始化资源配置
	 * 
	 * @create 2008-7-8 下午02:01:34 chennp
	 * @history
	 */
	public void initResourceConfig() {
		this.resourceDao.initResourceConfig();
	}

}
