package com.dolphin.dao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.core.utils.ExtendClassLoader;
import com.core.utils.ReadResources;
import com.dolphin.domain.resource.Resource;

/***********************************************************************************************************
 * 功能资源builder
 *
 * @author: chennp
 * @since: 2008-7-4 下午02:19:37
 * @history:
 * ***********************************************
 * @file: ResourceConfigBuilder.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 **********************************************************************************************************/
public class ResourceConfigBuilder {

	private static final Logger logger = Logger
			.getLogger(ResourceConfigBuilder.class);


	private List<Resource> resourceList; // 所有资源的list
	private Map<String, String> typeAliasMap;// 别名与类型Map
	private String configLocation;// 配置文件路径
	private Map<String, List<Resource>> urlResourceMap;
	private Map<String,Resource> idResourceMap;



	public ResourceConfigBuilder() {
	}

	public ResourceConfigBuilder(String configLocation) throws IOException {
		this.setConfigLocation(configLocation);
		init();
	}

	public void init() throws IOException {
		InputStream stream = null;
		try{
			stream = ReadResources.getResourceAsStream(getConfigLocation());
			ResourceConfigParser resourceConfigParser = new ResourceConfigParser();
			this.resourceList = resourceConfigParser.parse(stream);
			this.typeAliasMap = resourceConfigParser.getTypeAliasMap();
		}finally{
			//added by yanghb20090116
			try{
				if(stream!=null){
					stream.close();
				}
			}catch(Exception e){
				//throw new Exception("ResourceConfigBuilder:init()stream  关闭错误");
			}
		}

	}

	/**
	 * 从配置文件获得所有主要资源并以同一组资源的树形式组装
	 *
	 * @param allResourcesList
	 * @return
	 * @create 2008-7-4 下午02:22:50 chennp
	 * @history
	 */
	protected List<Resource> getAllMainResourceList(List<Resource> allResourceList) {
		List<Resource> allMainResourcesList = new ArrayList<Resource>();
		for (int i = 0, size = allResourceList.size(); i < size; i++) {
			Resource resource = allResourceList.get(i);
			if (resource.isRootResource()) {
				resource.setLevel(0);
				allMainResourcesList.add(resource);
				continue;
			}
			setSubResources(allResourceList, i, resource);
		}
		for(Resource resource : allMainResourcesList){
			setResourceLevel(resource);
		}
		return allMainResourcesList;
	}

	/**
	 * 获得资源的ID与资源map，同时检查ID是否被重复定义
	 *
	 * @param allResourcesList
	 * @return
	 * @create 2008-7-4 下午02:24:25 chennp
	 * @history
	 */
	protected Map<String, Resource> getIDResourceMap(List<Resource> allResourcesList) {
		Map<String, Resource> idResourceMap = new TreeMap<String, Resource>();
		for (Resource resource : allResourcesList) {
			if (StringUtils.isNotBlank(resource.getId())) {
				if (idResourceMap.containsKey(resource.getId()))
					throw new IllegalXMLTagException("Duplicate ID: " + resource.getId() + " (ID被重复定义!)");
				idResourceMap.put(resource.getId(), resource);
			}
		}
		return idResourceMap;
	}

	/**
	 * 获得资源的URI与资源列表map
	 *
	 * @param allResourcesList
	 * @return
	 * @create 2008-7-4 下午02:25:19 chennp
	 * @history
	 */
	protected Map<String, List<Resource>> getURIResourceMap(List<Resource> allResourcesList) {
		Map<String, List<Resource>> uriReosurceMap = new TreeMap<String, List<Resource>>();
		for (int i = 0, size = allResourcesList.size(); i < size; i++) {
			Resource resource = allResourcesList.get(i);
			if (StringUtils.isNotBlank(resource.getUrl())) {
				List<Resource> sameUriResource = new ArrayList<Resource>(1);
				if (uriReosurceMap.containsKey(resource.getUrl())) {
					sameUriResource = uriReosurceMap.get(resource.getUrl());
				}
				//设置其子节点
				Collection<Resource> subResourcesList = getSubResources(resource);
				for (int j = i + 1; j < size; j++) {
					Resource subResource = allResourcesList.get(j);
					if (subResource.isParentResource(resource)) {
						subResourcesList.add(subResource);
					}
				}
				resource.setSubResources(subResourcesList);
				sameUriResource.add(resource);
				uriReosurceMap.put(resource.getUrl(), sameUriResource);
			}
		}
		return uriReosurceMap;
	}

	/**
	 * 获取mainMenu指定的menuTag作为菜单标签的菜单列表
	 *
	 * @param allResourcesList
	 * @return
	 * @create 2008-7-4 下午02:25:47 chennp
	 * @history
	 */
	protected List<Resource> getMenuResourceList(List<Resource> allResourcesList) {
		List<Resource> menuResourceList = new ArrayList<Resource>();
		Class menuResource = Object.class;
		for (int i = 0, size = allResourcesList.size(); i < size; i++) {
			Resource resource = allResourcesList.get(i);
			if (resource.isRootResource()) {
				try {
					menuResource = ExtendClassLoader.loadClassForName(getTypeAliasMap().get(resource.getMenuTag()));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				resource.setLevel(0);
				menuResourceList.add(resource);

			} else if (resource.getClass() != menuResource) {// 是否同一类型
				continue;
			} else{
			  setSubResources(allResourcesList, i, resource);
			}
		}
		for(Resource resource : menuResourceList){
			setResourceLevel(resource);
		}
		/*for(Resource resource : allResourcesList){
			if (resource.isRootResource()) {
				try {
					menuResource = ExtendClassLoader.loadClassForName(getTypeAliasMap().get(resource.getMenuTag()));
				} catch (ClassNotFoundException e) {
					logger.error(e);
				}
				resource.setLevel(0);
				menuResourceList.add(resource);
				setSubResources(allResourcesList,resource);
			}
		}*/
		return menuResourceList;
	}

	private void setResourceLevel(Resource resource){
		Collection<Resource> subResources = getSubResources(resource);
		for(Resource subResource : subResources){
			subResource.setLevel(resource.getLevel() + 1);
			setResourceLevel(subResource);
		}
	}

	private void setSubResources(List<Resource> allResourcesList,Resource parentResource){
		Collection<Resource> subResources = getSubResources(parentResource);
		for(Resource resource : allResourcesList){
			if(resource.isParentResource(parentResource)){
				subResources.add(resource);
				setSubResources(allResourcesList,resource);
			}
		}
		parentResource.setSubResources(subResources);
	}


	/**
	 * @param allResourcesList
	 * @param endIndex
	 * @param resource
	 * @create  2008-7-8 下午10:24:51 chennp
	 * @history
	 */
	private void setSubResources(List<Resource> allResourcesList, int endIndex, Resource resource) {
		if (endIndex >= allResourcesList.size())
			return;

		for (int j = 0; j < endIndex; j++) {
			Resource parentResource = allResourcesList.get(j);
			if (resource.isParentResource(parentResource)) {
				Collection<Resource> subResourcesList = getSubResources(parentResource);
				//resource.setLevel(parentResource.getLevel()+1);
				subResourcesList.add(resource);
				parentResource.setSubResources(subResourcesList);
				resource.setParentResource(parentResource);
			}
		}
	}

	/**
	 * 获取子资源列表
	 *
	 * @param parentResource
	 * @return
	 * @create 2008-7-4 下午02:27:57 chennp
	 * @history
	 */
	private Collection<Resource> getSubResources(Resource parentResource) {
		Collection<Resource> resoureList = parentResource.getSubResources();
		if (null == resoureList) {
			resoureList = new ArrayList<Resource>(1);
		}
		return resoureList;
	}

	public Map<String, Resource> getIdResourceMap() {
		if(null == idResourceMap)
			idResourceMap = getIDResourceMap(getResourceList());
		return idResourceMap;
	}

	public List<Resource> getMainResourceList() {
		return getAllMainResourceList(getResourceList());
	}

	public List<Resource> getResourceList() {
		return cloneResourceList(this.resourceList);
	}

	public Map<String, List<Resource>> getUriResourceMap() {
		if(null == urlResourceMap)
			urlResourceMap = getURIResourceMap(getResourceList());
		return urlResourceMap;
	}

	public String getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}

	public List<Resource> getMenuResourceList() {
		return getMenuResourceList(getResourceList());
	}

	public Map<String, String> getTypeAliasMap() {
		return typeAliasMap;
	}

	/**
	 * 克隆一个list
	 *
	 * @param resourceList
	 * @return
	 * @create 2008-7-4 下午02:28:38 chennp
	 * @history
	 */
	private List<Resource> cloneResourceList(List<Resource> resourceList) {
		List<Resource> cloneResourceList = new ArrayList<Resource>(resourceList.size());
		for (Resource resource : resourceList) {
			try {
				cloneResourceList.add((Resource) BeanUtils.cloneBean(resource));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return cloneResourceList;
	}
}
