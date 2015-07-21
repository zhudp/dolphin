package com.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;



/**
 * BeanUtils
 * 
 * @author: wanglf
 * @since: Dec 20, 2007 10:45:28 AM
 */
public class PBeanUtils extends org.apache.commons.beanutils.BeanUtils{
	protected static final Log logger = LogFactory.getLog(PBeanUtils.class);

	/**
	 * 根据属性名称，根据属性名称给POJO赋值
	 */
	@SuppressWarnings("unchecked")
	public static <T> void setBeanPropertyByName(T entity, String propertyName, String propertyValue) throws Exception {
		try {
			if (!describe(entity).containsKey(propertyName))
				return;

			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, entity.getClass());
			Class propertyType = propertyDescriptor.getPropertyType();
			Method setMethod = propertyDescriptor.getWriteMethod();

			// String型设置为""，而不是null
			if (propertyType == String.class) {
				setMethod.invoke(entity, propertyValue);
				return;
			}

			if (StringUtils.isBlank(propertyValue))
				return;
          
			if (propertyType == Long.class || propertyType == long.class) {
				setMethod.invoke(entity, Long.parseLong(propertyValue));
			} else if (propertyType == Byte.class || propertyType == byte.class) {
				setMethod.invoke(entity, Byte.parseByte(propertyValue));
			} else if (propertyType == Integer.class || propertyType == int.class) {
				setMethod.invoke(entity, Integer.parseInt(propertyValue));
			} else if (propertyType == Short.class || propertyType == short.class) {
				setMethod.invoke(entity, Short.parseShort(propertyValue));
			} else if (propertyType == Float.class || propertyType == float.class) {
				setMethod.invoke(entity, Float.parseFloat(propertyValue));
			} else if (propertyType == Double.class || propertyType == double.class) {
				setMethod.invoke(entity, Double.parseDouble(propertyValue));
			} else if (propertyType == BigDecimal.class) {
				setMethod.invoke(entity, new BigDecimal(propertyValue));
			} else if (propertyType == java.util.Date.class) {
				setMethod.invoke(entity, DateUtils.stringToUtilDate(propertyValue));
			} else if (propertyType == java.sql.Date.class) {
				setMethod.invoke(entity, DateUtils.stringToSqlDate(propertyValue));
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception("根据属性名称给POJO赋值时，出现异常：" + "[" + propertyName + ": " + propertyValue + "]");
		}
	}
	
	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}

	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}
	public static void forceSetProperty(Object object, String propertyName, Object newValue)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	public static Object invokePrivateMethod(Object object, String methodName, Object... params)
			throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class<?> clazz = object.getClass();
		Method method = null;
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:" + clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	public static List<Field> getFieldsByType(Object object, Class type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}
	public static Class getPropertyType(Class type, String name) throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	public static Method getGetterMethod(Class<?> type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static String getSetterName(String fieldName) {
		Assert.hasText(fieldName, "FieldName required");

		return "set" + StringUtils.capitalize(fieldName);
	}
	 public static Object getInstanceByClassName(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (null == className) {
			throw new IllegalArgumentException("类名称不可以为空");
		}
		return ExtendClassLoader.loadClassForName(className).newInstance();
	}
	/**
	 *  获取对象的属性值，并将值转换为String
	 *  
	 * @param bean
	 * @param propertyName
	 * @return 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	* @create  2010-12-17 下午02:50:02 chenzy
	* @history
	 */
	@SuppressWarnings("unchecked")
	public static String GetBeanPropertyValue(Object bean,String propertyName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String sv ="";
		Object value = PropertyUtils.getProperty(bean, propertyName);
		if(value == null) {
			return sv;
		}
		Class propertyType= value.getClass(); 
	    if (propertyType == java.util.Date.class) {
			
			sv = DateUtils.toDateString((java.util.Date)value);
		}else{
			sv= ConvertUtils.convert(value);
		}
		if(sv==null){
			sv ="";
		}
		return sv;
		
	}
	

}
