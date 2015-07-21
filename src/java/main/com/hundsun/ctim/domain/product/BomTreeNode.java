package com.hundsun.ctim.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.core.BaseEntity;
import com.hundsun.ctim.domain.material.Material;

/**
 * BOM树节点
 * @author Administrator
 *
 */
public class BomTreeNode extends BaseEntity {
    private static final long serialVersionUID = -862161803209564093L;
    
    /** 节点类型：part:部件,material:原材料 */
    private String type;
    
    /** 所属产品ID */
    private Long productId;
    
    /** 配件ID */
    private Long partId;
    
	/** ID */
    private Long id;

    /** 名称 */
    private String name;

    /** 编号 */
    private String no;

	/** 数量 */
    private int number;

    /** 规格 */
    private String standard;

	/** 单位 */
    private String unit;

    /** 部件重量 */
    private BigDecimal weight;

    /** 部件面积 */
    private BigDecimal area;

    /** 备注 */
    private String remark;

	/** 原料列表 */
    private List<Material> children = new ArrayList<Material>();
    
	/** 树样式 */
    private String iconCls ="task-folder";

	/** 是否展开 */
    private boolean expanded = true;
    
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Material> getChildren() {
		return children;
	}

	public void setChildren(List<Material> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
    
    public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    
    public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}