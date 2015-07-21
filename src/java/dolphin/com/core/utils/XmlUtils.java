package com.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.Properties;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;



/**
 * XML帮助类
 * 
 * @author: wanglf
 * @since: Feb 3, 2008 2:01:09 PM
 */
@SuppressWarnings("unchecked")
public class XmlUtils {
	/**
	 * 读取指定XML文件的根元素.
	 */
	public static org.jdom.Element getRootElement(String fileURI, Class clazz) {
		Element rootElement = null;
		InputStream stream = null;
		try {
			stream = clazz.getResourceAsStream(fileURI);
			rootElement = new SAXBuilder().build(stream).getRootElement();
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return rootElement;
	}
	public static Element getRootElement(String fileURI) {
		try {
			return getRootElement(ReadResources.getResourceAsStream(fileURI));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static Element getRootElement(Reader reader) {
		try {
			return new SAXBuilder().build(reader).getRootElement();
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally{
			if(null != reader){
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	public static Element getRootElement(InputStream stream) {
		try {
			return new SAXBuilder().build(stream).getRootElement();
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally{
			if(null != stream){
				try {
					stream.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static Properties parseAttributes(Element element) {
		Properties attributes = new Properties();
		Iterator<Attribute> attributeNodes = element.getAttributes().iterator();
		while(attributeNodes.hasNext()){
			Attribute attribute = attributeNodes.next();
			attributes.put(attribute.getName(), attribute.getValue());
		}
		return attributes;
	}
}
