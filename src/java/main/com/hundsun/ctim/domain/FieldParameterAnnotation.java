package com.hundsun.ctim.domain;

import java.lang.annotation.*;

/**
 * 字段反射注解
 * 
 * @author: jinrey
 * @since: 2012-7-23 下午07:48:26
 * @history: 
 ************************************************ 
 * @file: FieldParameterAnnotation.java
 * @Copyright: 2010 HundSun Electronics Co.,Ltd. All right reserved.
 ***********************************************
 */
@Target( { ElementType.FIELD, ElementType.PARAMETER }) // 用于字段，参数
@Retention(RetentionPolicy.RUNTIME) // 在运行时加载到JVM中
public @interface FieldParameterAnnotation {
	
	 // 定义一个没有默认值的String成员
	String describ();
	String mappingName() default "";
}
