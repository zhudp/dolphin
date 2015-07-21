package com.hundsun.ctim.domain;

import com.core.BaseEntity;

public class SerialNo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long noId;
	/** 编号前缀**/
	private String serialPrefix;
	/** 编号偏移量**/
	private Long serialNumber;
	/** 编号长度**/
	private Long noSize;
	/** 备注**/
	private String remark;
	/**
	 * 根据对象返回编号
	 * @return
	 */
	public String getNo(String connector){
		if(noSize<2){
			return null;
		}
		String fmt ="%1$,0"+noSize+"d";
		return serialPrefix+connector+String.format(fmt, serialNumber);
	}
	
	public String getNonothing(){
		return this.getNo("-");
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNo2(){
		return this.getNo("");
	}
	
	public Long getNoId() {
		return noId;
	}
	public void setNoId(Long noId) {
		this.noId = noId;
	}
	public String getSerialPrefix() {
		return serialPrefix;
	}
	public void setSerialPrefix(String serialPrefix) {
		this.serialPrefix = serialPrefix;
	}
	public Long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Long getNoSize() {
		return noSize;
	}
	public void setNoSize(Long noSize) {
		this.noSize = noSize;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
