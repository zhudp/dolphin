package com.dolphin.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.core.BaseEntity;

/**
 * @author yanghb
 * 
 */
public class Datadict extends BaseEntity implements Comparable {
	private static final long serialVersionUID = 4615219790401892652L;
	public static final String rootNode = "0";
	private Long id;

	private String isDeleted;

	private Date gmtCreate;

	private String creator;

	private Date gmtModify;

	private String modifier;

	private String resCode;

	private String resType;

	private String resName;

	private BigDecimal sortNum;

	private Long parentId;

	private Short maxlevel;

	private Long childnum;

	private String resNameSub;

	private String remark;

	private String dataRange;

	private String isDisable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public BigDecimal getSortNum() {
		return sortNum;
	}

	public void setSortNum(BigDecimal sortNum) {
		this.sortNum = sortNum;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Short getMaxlevel() {
		return maxlevel;
	}

	public void setMaxlevel(Short maxlevel) {
		this.maxlevel = maxlevel;
	}

	public Long getChildnum() {
		return childnum;
	}

	public void setChildnum(Long childnum) {
		this.childnum = childnum;
	}

	public String getResNameSub() {
		return resNameSub;
	}

	public void setResNameSub(String resNameSub) {
		this.resNameSub = resNameSub;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDataRange() {
		return dataRange;
	}

	public void setDataRange(String dataRange) {
		this.dataRange = dataRange;
	}

	public String getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}

	
	public int compareTo(Object o) {
		Datadict co = (Datadict) o;
		if (co.getParentId().equals(this.getParentId())) {
			//同一父节点比较排序号
			if (this.getSortNum() == null || co.getSortNum() == null)
				return 0;
			return this.getSortNum().compareTo(co.getSortNum());
		} else {
			return getId().compareTo(co.getId());
		}
	}

}