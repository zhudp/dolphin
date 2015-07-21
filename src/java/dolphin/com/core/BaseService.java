package com.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.core.utils.StringTool;

/**
 * 业务实现基类
 * 
 * @author: wanglf
 * @since: Dec 19, 2007 1:36:21 PM
 */
public abstract class BaseService {
	
	protected static final Log log = LogFactory.getLog(BaseService.class);
	
	
	/**
	 * 用compareProperties里面的属性来比较两个对象的对应值是否相同
	 * 
	 * @param objone
	 * @param objtwo
	 * @param compareProperties
	 *            所有需要比较的属性
	 * @return ture：两个对象的值不同
	 * @create  2009-1-6 下午01:53:35 yanghb
	 * @history  
	 */
	protected boolean compareValue(Object objone, Object objtwo,
			Collection<String> compareProperties) {
		for (String property : compareProperties) {
			try {
				String orginalValue = BeanUtils.getProperty(objone, property);
				String newValue = BeanUtils.getProperty(objtwo, property);
				
				if (StringTool.isBlank(orginalValue) && StringTool.isBlank(newValue)) {
					continue;
				}
				
				if (StringTool.isBlank(orginalValue) || StringTool.isBlank(newValue)) {
					return true;
				}
				
				if (!orginalValue.equals(newValue)) {
					return true;
				}
			} catch (IllegalAccessException e) {
				log.error(e);
			} catch (InvocationTargetException e) {
				log.error(e);
			} catch (NoSuchMethodException e) {
				log.error(e);
			}
		}
		return false;
	}
}
