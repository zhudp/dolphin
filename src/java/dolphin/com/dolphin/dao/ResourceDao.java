package com.dolphin.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dolphin.domain.resource.Resource;

/**
 * 功能资源Dao实现类
 * 
 * @author: chennp
 * @since: 2008-7-4 下午02:39:22
 * @history:
 ************************************************ 
 * @file: ResourceDaoXML.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ************************************************/
@Repository
public class ResourceDao {
	private String configLocation;
	private ResourceConfigBuilder resourceConfigBuilder;

	public List<Resource> getAll() {
		return resourceConfigBuilder.getResourceList();
	}

	public Resource get(String id) {
		return resourceConfigBuilder.getIdResourceMap().get(id);
	}

	public List<Resource> getMainResource() {
		return resourceConfigBuilder.getMainResourceList();
	}

	public List<Resource> getMenuResource() {
		return resourceConfigBuilder.getMenuResourceList();
	}

	public List<Resource> getByURL(String uri) {
		return resourceConfigBuilder.getUriResourceMap().get(uri);
	}

	public Map<String, List<Resource>> getURLResourceMap() {
		return resourceConfigBuilder.getUriResourceMap();
	}

	public List<Resource> queryByURL(String uri, boolean flag) {
		List<Resource> resourceList = new ArrayList<Resource>();
		final Map<String, List<Resource>> uriResourceMap = resourceConfigBuilder
				.getUriResourceMap();
		Iterator<String> uriIter = uriResourceMap.keySet().iterator();
		while (uriIter.hasNext()) {
			String uriKey = uriIter.next();
			if (isLike(uri, uriKey) || flag) {
				resourceList.addAll(uriResourceMap.get(uriKey));
			}
		}
		return resourceList;
	}

	private boolean isLike(String uri, String uriKey) {
		if (StringUtils.isBlank(uriKey) || StringUtils.isBlank(uriKey))
			return false;
		return StringUtils.contains(uriKey, uri);
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
		initResourceConfig();
	}

	public void initResourceConfig() {
		try {
			if (null == resourceConfigBuilder) {
				resourceConfigBuilder = new ResourceConfigBuilder(
						this.configLocation);
			} else {
				resourceConfigBuilder.init();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
