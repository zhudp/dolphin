package com.dolphin.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.jdom.Element;

import com.core.utils.ReadResources;
import com.core.utils.XmlUtils;
import com.dolphin.domain.resource.Resource;

/***********************************************************************************************************
 * 功能资源配置文件xml解析类
 * 
 * @author: chennp
 * @since: 2008-7-4 下午02:29:11
 * @history: ***********************************************
 * @file: ResourceConfigParser.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 **********************************************************************************************************/
public class ResourceConfigParser {
	public static final String typeAlias_ELEMENT = "typeAlias"; // 别名标签
	public static final String resource_ELEMENT = "resource"; // 资源标签
	public static final String resourceLocation = "resourceLocation";// 资源来源
	public static final String ALIAS = "alias";// 别名
	public static final String TYPE = "type";// 类型
	private ResourceDefinitionParser resourceDefinitionParser;// 资源定义类
	private Map<String, String> typeAliasMap;// 别名类型map

	public List<Resource> parse(Reader reader) throws IOException {
		Element rootElement = XmlUtils.getRootElement(reader);
		return parse(rootElement);
	}

	public List<Resource> parse(InputStream stream) throws IOException {
		Element rootElement = XmlUtils.getRootElement(stream);
		return parse(rootElement);
	}

	public List<Resource> parse(Element rootElement) throws IOException {
		this.setTypeAliasMap(parseTypeAliasMap(rootElement));
		resourceDefinitionParser = new ResourceDefinitionParser();
		resourceDefinitionParser.setTypeAliasMap(this.getTypeAliasMap());
		return parseResource(rootElement);
	}

	/**
	 * 解析功能资源xml
	 * 
	 * @param rootElement
	 * @return
	 * @throws IOException
	 * @create 2008-7-4 下午02:31:06 chennp
	 * @history
	 */
	protected List<Resource> parseResource(Element rootElement) throws IOException {
		if (rootElement == null) {
			throw new IllegalArgumentException("rootElement is null");
		}
		List<Resource> resourcesList = new ArrayList<Resource>();
		for (Element element : (List<Element>) rootElement.getChildren(resource_ELEMENT)) {
			Properties resourceProperties = XmlUtils.parseAttributes(element);
			if (StringUtils.isNotBlank(resourceProperties.getProperty(resourceLocation))) {// 若存在resourceLocation，则解析xml
				InputStream stream = null;
				try{
					stream = ReadResources.getResourceAsStream(resourceProperties.getProperty(resourceLocation));
					resourcesList.addAll(resourceDefinitionParser.parse(stream));
				}finally{
					//added by yanghb20090116
					try{
						if(stream!=null){
							stream.close();
						}
					}catch(Exception e){
						throw new IOException("ResourceConfigParser:parseResource()stream  关闭错误");
					}
				}
				
			} else {
				for (Element childElement : (List<Element>) element.getChildren()) {
					resourcesList.addAll(resourceDefinitionParser.parse(childElement));
				}
			}
		}
		return resourcesList;
	}

	/**
	 * 解析别名类型
	 * 
	 * @param rootElement
	 * @return
	 * @create 2008-7-4 下午02:30:51 chennp
	 * @history
	 */
	protected Map<String, String> parseTypeAliasMap(Element rootElement) {
		List<Element> nodeList = rootElement.getChildren(typeAlias_ELEMENT);
		Map<String, String> typeAliasMap = new HashMap<String, String>(nodeList.size());
		for (Element node : nodeList) {
			Properties typeAliasProperties = XmlUtils.parseAttributes(node);
			typeAliasMap.put(typeAliasProperties.getProperty(ALIAS), typeAliasProperties.getProperty(TYPE));
		}
		return typeAliasMap;
	}

	public Map<String, String> getTypeAliasMap() {
		return typeAliasMap;
	}

	public void setTypeAliasMap(Map<String, String> typeAliasMap) {
		this.typeAliasMap = typeAliasMap;
	}

}
