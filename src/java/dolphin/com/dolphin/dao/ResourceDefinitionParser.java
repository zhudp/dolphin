package com.dolphin.dao;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.StringUtils;
import org.jdom.Attribute;
import org.jdom.Element;

import com.core.utils.PBeanUtils;
import com.core.utils.XmlUtils;
import com.dolphin.domain.resource.Resource;


/** 
 * 功能资源定义xml解析
 * @author: chennp
 * @since: 2008-7-4  下午02:32:50
 * @history:
 ************************************************
 * @file: ResourceDefinitionParser.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************/
public class ResourceDefinitionParser {
	protected Map<String, String> typeAliasMap;//类型与别名map
	private final static String IGNORE_THIS_TAG = "ignore";//可忽略的标签

	public List<Resource> parse(Reader reader) {
		return parse(XmlUtils.getRootElement(reader));
	}

	public List<Resource> parse(InputStream stream) {
		return parse(XmlUtils.getRootElement(stream));
	}

	public List<Resource> parse(Element rootElement) {
		List<Resource> resourceSet = new ArrayList<Resource>();
		Queue<Element> visitNodeQueue = new ConcurrentLinkedQueue<Element>();
		visitNodeQueue.offer(rootElement);
		
		//广度优先遍历
		while (!visitNodeQueue.isEmpty()) {
			Element visitedNode = visitNodeQueue.poll();
			Resource resource = parseNode(visitedNode);
			resourceSet.add(resource);
			for (Element element : (List<Element>) visitedNode.getChildren()) {
				if (isIgnoreNode(element)){
					continue;
				}
				visitNodeQueue.offer(element);
			}
		}
//		do {
//			
//			visitedNode = visitNodeQueue.poll();
//		} while (!visitNodeQueue.isEmpty());
		
		return resourceSet;
	}

	
	/** 
	 * 指定标签是否可忽略
	 * @param node
	 * @return 
	 * @create  2008-7-4 下午02:33:19 chennp
	 * @history  
	 */
	private boolean isIgnoreNode(Element node) {
		if (node.getAttributes().isEmpty())
			return true;
		Properties properties = XmlUtils.parseAttributes(node);
		return StringUtils.isNotBlank(properties.getProperty(IGNORE_THIS_TAG)) 
					&& Boolean.parseBoolean(properties.getProperty(IGNORE_THIS_TAG));
	}

	private Resource parseNode(Element node) {
		Resource resource = getResourceFromNode(node);
		if (node.getParentElement() != null) {
			resource.setParentResource(getResourceFromNode(node.getParentElement()));
		}
//		if (node.getChildren().size() > 0) {
//			Collection<Resource> subResourcesList = new ArrayList<Resource>();
//			for (Element childNode : (List<Element>)node.getChildren()) {
//				subResourcesList.add(getResourceFromNode(childNode));
//			}
//			resource.setSubResources(subResourcesList);
//		}
		return resource;
	}

	private Resource getResourceFromNode(Element node) {
		String className = this.typeAliasMap.get(node.getName());
		try {
			Resource resource = (Resource) PBeanUtils.getInstanceByClassName(className);
			Iterator<Attribute> attributeNodes = node.getAttributes().iterator();
			while (attributeNodes.hasNext()) {
				Attribute element = attributeNodes.next();
				PBeanUtils.setBeanPropertyByName(resource, element.getName(), element.getValue());
			}
			//设置标签resource
			PBeanUtils.setBeanPropertyByName(resource, "tagName", node.getName());
			return resource;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new IllegalXMLTagException(node.getName() + "没有定义");
	}

	public void setTypeAliasMap(Map<String, String> typeAliasMap) {
		this.typeAliasMap = typeAliasMap;
	}
}
