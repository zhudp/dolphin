package com.dolphin.domain;

import java.sql.Date;

import com.core.BaseEntity;

public class SystemLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer slogId; // 日志编号

	private String userId; // 操作员账号

	private String ipAdd; // 操作员IP地址

	private String operType; // 操作类型

	private String operObjec; // 操作对象

	private String slogComment; // 日志内容

	private String slogCreateTime; // 日志创建时间

	private String slogType; // 日志类型

	private Date slogTime; // 查询时间

	// 查询条件
	private String userName; // 操作员姓名

	private String slogTypeName; // 日志类型名称

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIpAdd() {
		return ipAdd;
	}

	public void setIpAdd(String ipAdd) {
		this.ipAdd = ipAdd;
	}

	public String getOperObjec() {
		return operObjec;
	}

	public void setOperObjec(String operObjec) {
		this.operObjec = operObjec;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getSlogComment() {
		return slogComment;
	}

	public void setSlogComment(String slogComment) {
		if(slogComment.length()>700){
			slogComment = slogComment.substring(0, 700);
		}
		this.slogComment = slogComment;
	}

	public String getSlogCreateTime() {
		return slogCreateTime;
	}

	public void setSlogCreateTime(String slogCreateTime) {
		this.slogCreateTime = slogCreateTime;
	}

	public Integer getSlogId() {
		return slogId;
	}

	public void setSlogId(Integer slogId) {
		this.slogId = slogId;
	}

	public Date getSlogTime() {
		return slogTime;
	}

	public void setSlogTime(Date slogTime) {
		this.slogTime = slogTime;
	}

	public String getSlogType() {
		return slogType;
	}

	public void setSlogType(String slogType) {
		this.slogType = slogType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSlogTypeName() {
		if (this.getSlogType() == null) {
			return "";
		}
//		slogTypeName = ConstantMap.getInstance().getSlogTypeValue(
//				this.getSlogType());
		return slogTypeName;
	}

	public void setSlogTypeName(String slogTypeName) {
		this.slogTypeName = slogTypeName;
	}


}
