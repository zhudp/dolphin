package com.dolphin.domain.resource;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.core.BaseEntity;

/***********************************************************************************************************
 * 功能资源实体类
 * 
 * @author: chennp
 * @since: 2008-7-4 上午11:07:06
 * @history: ***********************************************
 * @file: Resource.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 **********************************************************************************************************/
public abstract class Resource extends BaseEntity {
	private String id; // 资源Id
	private String code; // 资源编码
	private String text; // 资源名称
	private String url; // URL链接地址
	private boolean ignoreURLExtension; // 是否忽略URL的扩展名
	private Integer level; // 级别
	private String iconPath; // 图标路径
	private String desc; // 资源描述
	// 以下是用于xml所需 特殊添加
	private String tagName; // xml标签名称
	private String menuTag; // 指定菜单标签
	private String contextPath; // 系统IP与路径
	private String config; // Ext json其他配置
	private boolean hasOwner; // 是否已拥有该资源
	private Resource parentResource;
	private Collection<Resource> subResources;

	public String getCode() {
		if (StringUtils.isBlank(this.code))
			return getId();
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIconPath() {
		if (StringUtils.isBlank(this.iconPath))
			return StringUtils.EMPTY;
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Resource getParentResource() {
		return parentResource;
	}

	public void setParentResource(Resource parentResource) {
		this.parentResource = parentResource;
	}

	public Collection<Resource> getSubResources() {
		return subResources;
	}

	public void setSubResources(Collection<Resource> subResources) {
		this.subResources = subResources;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		if (StringUtils.isBlank(this.url))
			return StringUtils.EMPTY;
		
		if (this.url.startsWith("/")) {
			return this.getRootResource().getContextPath() + this.url;
		}
		
		if (StringUtils.isNotBlank(this.getRootResource().getContextPath())) {
			return this.getRootResource().getContextPath() +"/"+ this.url;
		}
		
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isIgnoreURLExtension() {
		return ignoreURLExtension;
	}

	public void setIgnoreURLExtension(boolean ignoreURLExtension) {
		this.ignoreURLExtension = ignoreURLExtension;
	}

	public String getContextPath() {
		if(StringUtils.isBlank(this.contextPath))
			return StringUtils.EMPTY;
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getMenuTag() {
		return menuTag;
	}

	public void setMenuTag(String menuTag) {
		this.menuTag = menuTag;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String name) {
		this.tagName = name;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}
	public boolean isHasOwner() {
		return hasOwner;
	}

	public void setHasOwner(boolean hasOwner) {
		this.hasOwner = hasOwner;
	}
	
	/** 
	 * 是否根节点
	 * @return 
	 * @create  2008-7-4 上午11:08:55 chennp
	 * @history  
	 */
	public boolean isRootResource() {
		return this.getParentResource() == null;
	}

	/** 
	 * 是否拥有子节点
	 * @return 
	 * @create  2008-7-4 上午11:08:34 chennp
	 * @history  
	 */
	public Boolean hasChild() {
		return null != getSubResources() && getSubResources().size() > 0;
	}

	
	/** 
	 * 是否this的父节点
	 * @param parentResource
	 * @return 
	 * @create  2008-7-4 上午11:08:13 chennp
	 * @history  
	 */
	public boolean isParentResource(Resource parentResource) {
		if (null == this.getParentResource()) {
			return false;
		}
		return this.parentResource.equals(parentResource);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Resource) {
			Resource otherResource = (Resource) obj;
			return this.getId().equals(otherResource.getId());
		}
		return false;
	}
	
	/** 
	 * 获取根节点
	 * @return 
	 * @create  2008-7-8 上午10:44:13 chennp
	 * @history  
	 */
	public Resource getRootResource(){
		Resource rootResource = this;
		while(!rootResource.isRootResource()){
			rootResource = rootResource.getParentResource();
		}
		return rootResource;
	}
	public abstract String toJSONString();
	/** 
	 * 转化Json字符串
	 * @param haschecked 转化为json时 是否带有checked 属性
	 * @return 
	 * @create  2008-7-4 上午11:09:43 chennp
	 * @history  
	 */
	public abstract String toJSONString(boolean haschecked);

	
}
