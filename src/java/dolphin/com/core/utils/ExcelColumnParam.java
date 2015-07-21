package com.core.utils;
/**
 *  
 ************************************************
 * @file: ExcelColumnParam.java
 * @Copyright: 2007 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************
 * @package: com.hs.cis.domain
 * @class: ExcelColumnParam
 * @description: 
 * 
 * @author: chenzy
 * @since: 2007-11-7-14:23:14
 * @history:
*
 */
public class ExcelColumnParam {
	private String fieldName;
	private String label;
	private short labelWidth;
	public short getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(short labelWidth) {
		this.labelWidth = labelWidth;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
