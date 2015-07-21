package com.dolphin.sys;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

public class DataObjUtils {
	private static final Logger logger = Logger.getLogger(DataObjUtils.class);

    public static Object getBeanPropertyValue(Object bean, String strPropertyName) throws ClassNotFoundException, NoSuchMethodException {
        Method propertyMethod = null;
        String strGetPropertyMethodName = getGetMethodNameByPropertyName(strPropertyName);
        try {
            Class[] clsParamter = {};
            propertyMethod = bean.getClass().getMethod(strGetPropertyMethodName, clsParamter);
        } catch (NoSuchMethodException e) {
        	logger.debug("方法:"+strPropertyName+"未找到");
            DataObjUtils.logger.debug("Method \"" + strGetPropertyMethodName + "\" not found");
            throw e;
        }
        if (!Modifier.isPublic(propertyMethod.getModifiers())) {
            logger.debug("方法:"+strPropertyName+"必须为public");
            DataObjUtils.logger.debug("Method \"" + strGetPropertyMethodName + "\" should be public");
        }

        Object[] propertyValueParams = {};
        try {
            return propertyMethod.invoke(bean, propertyValueParams);
        } catch (Exception e) {
            throw new NoSuchMethodException(e.getMessage());
        }
    }


    public static Object invokeByMethodName(Object bean, String methodName, Object[] propertyValueParams) throws NoSuchMethodException {
        Method[] methods = bean.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                try {
                    if (null == propertyValueParams){
                        Object[] propertyParams = {};
                        return methods[i].invoke(bean, propertyParams);
                    }
                    return methods[i].invoke(bean, propertyValueParams);
                } catch (Exception e) {
                    System.out.println("the method which named "+methodName+" can't be invoke,maybe parameters class are different");
                }
            }
        }
        return null;
    }

    public static Class getReturnTypeByMethodName(Object bean, String methodName) {
        Method[] methods = bean.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                return methods[i].getReturnType();
            }
        }
        return null;
    }

    public static Class[] getParameterTypeByMethodName(Object bean, String methodName) {
        Method[] methods = bean.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                return methods[i].getParameterTypes();
            }
        }
        return null;
    }

    public static String getSetMethodNameByPropertyName(String strPropertyName) {
        return "set" + getFirstUpperCasePropertyName(strPropertyName);
    }

    public static String getGetMethodNameByPropertyName(String strPropertyName) {
        return "get" + getFirstUpperCasePropertyName(strPropertyName);
    }

    private static String getFirstUpperCasePropertyName(String strPropertyName) {
        String s = "";
        if (strPropertyName != null && strPropertyName.length() > 0) {
            s = s.concat(strPropertyName.substring(0, 1).toUpperCase());
            s = s.concat(strPropertyName.substring(1));
        }
        return s;
    }
}
