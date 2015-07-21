package com.dolphin.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Service;

import com.dolphin.domain.Role;
import com.dolphin.domain.RoleItemAssign;
import com.dolphin.domain.resource.Resource;
import com.dolphin.service.ResourceService;
import com.dolphin.service.RoleService;
/**
 * 获取数据库中配置的权限资源与角色对应关系数据集
 * @author: chenzy
 * @since: 2010-10-31  下午02:19:44
 * @history:
 ************************************************
 * @file: InvocationSecurityMetadataSourceService.java
 * @Copyright: 2010 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ***********************************************
 */
@Service("securityMetadataService")
public class InvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	private static final Logger logger = Logger
			.getLogger(InvocationSecurityMetadataSourceService.class);

	private RoleService roleService;
    
    
	private ResourceService resourceService;
    
    public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public ResourceService getResourceService() {
		return resourceService;
	}
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    public InvocationSecurityMetadataSourceService(ResourceService resourceService, RoleService roleService) throws Exception{
    	this.resourceService =resourceService;
    	this.roleService = roleService;
    	loadResourceDefine();
    }
	public void loadResourceDefine()  {
		if(resourceMap !=null)
			resourceMap.clear();
		else
		  resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		Collection<ConfigAttribute> atts ;
		// 通过数据库中的信息设置，resouce和role
		for (Role item : this.roleService.getAll()) {	
			ConfigAttribute ca = new SecurityConfig("A_"
					+ item.getId().toString());
			List<RoleItemAssign> list = roleService.getRoleItems(item.getId());
			for (RoleItemAssign roleItemAssign : list) {
				Resource resource = resourceService.get(roleItemAssign
						.getRoleItemId());
				if (resource != null&&!resource.getUrl().equals("")) {
					if(resourceMap.containsKey(resource.getUrl())){
						 atts =resourceMap.get(resource.getUrl());
						 if(!atts.contains(ca))
						   atts.add(ca);
					}else{
						atts = new ArrayList<ConfigAttribute>();
						atts.add(ca);
						resourceMap.put(resource.getUrl(), atts);
					}
					
				}
			}
		}
	}

	// According to a URL, Find out permission configuration of this URL.
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		if (logger.isDebugEnabled()) {
			logger.debug("getAttributes(Object) - start"); //$NON-NLS-1$  
		}
		// guess object is a URL.
		String url = ((FilterInvocation) object).getRequestUrl();
		if (logger.isDebugEnabled()) {
			logger.debug("打印："+url); //$NON-NLS-1$  
		}
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (urlMatcher.pathMatchesUrl(resURL+"*",url)) {
				Collection<ConfigAttribute> returnCollection = resourceMap
						.get(resURL);
				if (logger.isDebugEnabled()) {
					logger.debug("getAttributes(Object) - end"); //$NON-NLS-1$  
				}
				return returnCollection;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getAttributes(Object) - end"); //$NON-NLS-1$  
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

}
